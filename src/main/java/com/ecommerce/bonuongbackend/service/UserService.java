package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.MailDto;
import com.ecommerce.bonuongbackend.dto.ResponseDto;
import com.ecommerce.bonuongbackend.dto.user.*;
import com.ecommerce.bonuongbackend.mail.MailTemplate;
import com.ecommerce.bonuongbackend.model.CustomUserDetails;
import com.ecommerce.bonuongbackend.model.InactivatedUser;
import com.ecommerce.bonuongbackend.model.User;
import com.ecommerce.bonuongbackend.repository.InactivatedUserRepository;
import com.ecommerce.bonuongbackend.repository.UserRepository;
import com.ecommerce.bonuongbackend.security.JwtAuthenticationFilter;
import com.ecommerce.bonuongbackend.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    private InactivatedUserRepository inactivatedUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SendGridMailService sendGridMailService;
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${client-url}")
    private String CLIENT_URL;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if(username.contains("@"))
            user = userRepository.findByEmail(username);
        else
            user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user == null)
            throw new UsernameNotFoundException(userId);
        return new CustomUserDetails(user.get());
    }

    public GetUserResponseDto getUser(HttpServletRequest request) {
        try {
            JwtProvider tokenProvider = new JwtProvider();
            String jwt = new JwtAuthenticationFilter().getJwtFromRequest(request);
            User user = null;
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(JWT_SECRET, jwt)) {
                String userId = tokenProvider.getUserIdFromJWT(JWT_SECRET, jwt);
                user = userRepository.findById(userId).get();
            }
            if(user == null)
                return new GetUserResponseDto(400,false,null);
            return new GetUserResponseDto(200,true,user);
        } catch (Exception exception) {
            log.error(exception.toString());
        }
        return null;
    }

    public ResponseDto register(RegisterDto registerDto) {
        if (Objects.nonNull(userRepository.findByEmail(registerDto.getEmail())))
            return new ResponseDto(400,false, "Email đã tồn tại");
        if(Objects.nonNull(userRepository.findByUsername(registerDto.getUsername())))
            return new ResponseDto(400,false, "Tên đăng nhập đã tồn tại");
        if(Objects.nonNull(userRepository.findByPhone(registerDto.getPhone())))
            return new ResponseDto(400,false, "Số điện thoại đã tồn tại");
        if(registerDto.getUsername().contains(" "))
            return new ResponseDto(400,false, "Tên đăng nhập không được chứa khoảng trắng");

        try {
            InactivatedUser inactivatedUser = inactivatedUserRepository.findByEmail(registerDto.getEmail());
            if (Objects.nonNull(inactivatedUser))
                inactivatedUserRepository.deleteById(inactivatedUser.getId());

            InactivatedUser user = new InactivatedUser(
                    registerDto.getFullName(),
                    registerDto.getEmail(),
                    registerDto.getUsername(),
                    passwordEncoder.encode(registerDto.getPassword()),
                    registerDto.getPhone(),
                    registerDto.getAddress());
            inactivatedUserRepository.insert(user);
            // accessToken
            String accessToken = jwtProvider.generateTokenRegister(JWT_SECRET, user);
            // activation mail
            MailDto emailContent = MailTemplate.activationMail(CLIENT_URL, accessToken, user.getId(), user.getUsername());
            sendGridMailService.sendHTML(user.getEmail(), emailContent);
            return new ResponseDto(200, true, "Đăng ký thành công");
        } catch (Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500, false, "Lỗi Server");
        }
    }

    public ResponseDto activate(ActivateDto activateDto) {
        try{
            JwtProvider jwtProvider = new JwtProvider();
            String token = activateDto.getToken();
            if( !jwtProvider.validateToken(JWT_SECRET, token) || !activateDto.getId().equals(jwtProvider.getUserIdFromJWT(JWT_SECRET,token)) )
                return new ResponseDto(400, false, "Token hoặc ID không hợp lệ.");

            InactivatedUser user = inactivatedUserRepository.findById(activateDto.getId()).get();
            if(Objects.isNull(user))
                return new ResponseDto(404, false, "Người dùng không tồn tại.");

            User newUser = new User(
                    user.getFullName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getAddress()
            );
            userRepository.insert(newUser);
            inactivatedUserRepository.deleteById(user.getId());
            MailDto emailContent = MailTemplate.welcomeMail(CLIENT_URL, newUser.getUsername());
            sendGridMailService.sendHTML(newUser.getEmail(), emailContent);

            return new ResponseDto(200, true, "Kích hoạt tài khoản thành công!");
        } catch (Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500, false, "Lỗi Server");
        }
    }

    public LoginResponseDto login(LoginDto loginDto) {
        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginDto.getUsername(),
//                            loginDto.getPassword()
//                    ));
//            SecurityContextHolder.getContext().setAuthentication(authenticate);
            User user = null;
            String username = loginDto.getUsername();
            if(username.contains("@"))
                user = userRepository.findByEmail(username);
            else
                user = userRepository.findByUsername(username);
            if(user == null)
                return new LoginResponseDto(404, false, "Wrong username or password");
            if(user.getIsBlock() == true)
                return new LoginResponseDto(403, false, "Tài khoản này đã bị khóa, vui lòng thử lại sau!");
            if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
                return new LoginResponseDto(400, false, "Wrong username or password");

            String token = jwtProvider.generateToken(JWT_SECRET, user.getId());
            return new LoginResponseDto(200, true, "Đăng nhập thành công", token);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new LoginResponseDto(500, false, "Lỗi Server");
        }
    }

    public ResponseDto sendMailResetPassword(SendMailResetPasswordDto sendMailResetPasswordDto) {
        try {
            User user = userRepository.findByEmail(sendMailResetPasswordDto.getEmail());
            if (Objects.isNull(user))
                return new ResponseDto(404,false, "Không tìm thấy địa chỉ email");
            String accessToken = jwtProvider.generateTokenResetPassword(JWT_SECRET, user);
            MailDto emailContent = MailTemplate.resetPasswordMail(CLIENT_URL, accessToken, user.getId());
            sendGridMailService.sendHTML(user.getEmail(), emailContent);
            return new ResponseDto(200, true, "Đã gửi email xác nhận");
        } catch(Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500, false, "Lỗi Server");
        }
    }

    public ResponseDto resetPassword(ResetPasswordDto resetPasswordDto) {
        try {
            JwtProvider jwtProvider = new JwtProvider();
            String token = resetPasswordDto.getToken();
            if( !jwtProvider.validateToken(JWT_SECRET, token) || !resetPasswordDto.getId().equals(jwtProvider.getUserIdFromJWT(JWT_SECRET,token)) )
                return new ResponseDto(400, false, "Token hoặc ID không hợp lệ.");

            Optional<User> user = userRepository.findById(resetPasswordDto.getId());
            if (Objects.isNull(user.get()))
                return new ResponseDto(404,false, "Người dùng không tồn tại");

            user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
            userRepository.save(user.get());

            MailDto emailContent = MailTemplate.welcomeMail(CLIENT_URL, user.get().getUsername());
            sendGridMailService.sendHTML(user.get().getEmail(), emailContent);
            return new ResponseDto(200, true, "Đặt lại mật khẩu thành công!");
        } catch(Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500, false, "Lỗi Server");
        }
    }

    public ResponseDto contact(ContactDto contactDto) {
        try {
            List<User> admins = userRepository.findAllByRole("ADMIN");
            if(admins.size() > 0) {
                MailDto emailContent = MailTemplate.contactMail(contactDto.getEmail(), contactDto.getFullName(), contactDto.getContent(), contactDto.getPhone());
                admins.forEach(admin -> {
                    sendGridMailService.sendHTML(admin.getEmail(), emailContent);
                });
            }
            return new ResponseDto(200, true, "Gửi email liên hệ thành công");
        } catch(Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500, false, "Lỗi Server");
        }
    }
}
