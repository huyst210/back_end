package com.ecommerce.bonuongbackend.config;

import com.ecommerce.bonuongbackend.model.User;
import com.ecommerce.bonuongbackend.repository.UserRepository;
import com.ecommerce.bonuongbackend.security.JwtAuthenticationFilter;
import com.ecommerce.bonuongbackend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserRepository userRepository = new UserRepository() {
        @Override public User findByEmail(String email) {return null;}
        @Override public User findByUsername(String username) {return null;}
        @Override public List<User> findAllByRole(String role) {return null;}
        @Override public User findByPhone(String phone) {return null;}
        @Override public <S extends User> List<S> saveAll(Iterable<S> entities) {return null;}
        @Override public List<User> findAll() {return null;}
        @Override public List<User> findAll(Sort sort) {return null;}
        @Override public <S extends User> S insert(S entity) {return null;}
        @Override public <S extends User> List<S> insert(Iterable<S> entities) {return null;}
        @Override public <S extends User> List<S> findAll(Example<S> example) {return null;}
        @Override public <S extends User> List<S> findAll(Example<S> example, Sort sort) {return null;}
        @Override public Page<User> findAll(Pageable pageable) {return null;}
        @Override public <S extends User> S save(S entity) {return null;}
        @Override public Optional<User> findById(String s) {return Optional.empty();}
        @Override public boolean existsById(String s) {return false;}
        @Override public Iterable<User> findAllById(Iterable<String> strings) {return null;}
        @Override public long count() {return 0;}
        @Override public void deleteById(String s) {}
        @Override public void delete(User entity) {}
        @Override public void deleteAllById(Iterable<? extends String> strings) {}
        @Override public void deleteAll(Iterable<? extends User> entities) {}
        @Override public void deleteAll() {}
        @Override public <S extends User> Optional<S> findOne(Example<S> example) {return Optional.empty();}
        @Override public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {return null;}
        @Override public <S extends User> long count(Example<S> example) {return 0;}
        @Override public <S extends User> boolean exists(Example<S> example) {return false;}
        @Override public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {return null;}
    };
    private UserService userService = new UserService(userRepository);
    private AppConfig appConfig = new AppConfig();

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(appConfig.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}