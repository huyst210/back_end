package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.account.AccountDto;
import com.ecommerce.bonuongbackend.dto.account.AccountResponseDto;
import com.ecommerce.bonuongbackend.dto.account.GetAccountsResponseDto;
import com.ecommerce.bonuongbackend.dto.account.UserAccountDto;
import com.ecommerce.bonuongbackend.model.User;
import com.ecommerce.bonuongbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public GetAccountsResponseDto getAccounts(String role) {
        try {
            List<User> users = userRepository.findAllByRole(role.toUpperCase(Locale.ROOT));
            return new GetAccountsResponseDto(200,true,"Lấy dữ liệu thành công",users);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetAccountsResponseDto(500,false,"Lỗi Server");
        }
    }

    public AccountResponseDto updateAccount(String id, AccountDto updateAccountDto) {
        try {
            User user = userRepository.findById(id).get();
            if(user == null)
                return new AccountResponseDto(404,false,"Không tìm thấy tài khoản");
            user.setRole(updateAccountDto.getRole());
            user.setIsBlock(updateAccountDto.getBlock());
            userRepository.save(user);
            return new AccountResponseDto(200,true,"Sửa tài khoản thành công", user);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new AccountResponseDto(500,false,"Lỗi Server");
        }
    }

//    public AccountResponseDto deleteAccount(String id) {
//        try {
//            User user = userRepository.findById(id).get();
//            if(user == null)
//                return new AccountResponseDto(404,false,"Không tìm thấy tài khoản");
//            userRepository.delete(user);
//            return new AccountResponseDto(200,true,"Xoá tài khoản thành công", user);
//        } catch (Exception exception) {
//            log.error(exception.toString());
//            return new AccountResponseDto(500,false,"Lỗi Server");
//        }
//    }

    public AccountResponseDto updateUserAccount(String userId, UserAccountDto updateAccountDto) {
        try {
            User user = userRepository.findById(userId).get();
            if(user == null)
                return new AccountResponseDto(404,false,"Không tìm thấy tài khoản");
            if(!Objects.equals(user.getPhone(), updateAccountDto.getPhone())) {
                User userPhoneExist = userRepository.findByPhone(updateAccountDto.getPhone());
                if(userPhoneExist != null) {
                    return new AccountResponseDto(400,false,"Số điện thoại đã tồn tại");
                }
            }
            user.setFullName(updateAccountDto.getFullName());
            user.setPhone(updateAccountDto.getPhone());
            user.setAddress(updateAccountDto.getAddress());
            user.setAvatar(updateAccountDto.getAvatar());
            userRepository.save(user);
            return new AccountResponseDto(200,true,"Sửa thông tin thành công", user);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new AccountResponseDto(500,false,"Lỗi Server");
        }
    }
}
