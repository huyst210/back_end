package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAllByRole(String role);
    User findByPhone(String phone);
}
