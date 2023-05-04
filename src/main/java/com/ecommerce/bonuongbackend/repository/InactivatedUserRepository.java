package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.InactivatedUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InactivatedUserRepository extends MongoRepository<InactivatedUser, String> {
    InactivatedUser findByEmail(String email);
}
