package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
