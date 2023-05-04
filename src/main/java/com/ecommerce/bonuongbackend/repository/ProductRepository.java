package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.Category;
import com.ecommerce.bonuongbackend.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findProductByCategory(Category category);
    @Query(sort = "{'createdAt': -1}")
    List<Product> findAllByDescriptionIsNotNull(PageRequest pageRequest);
    @Query(sort = "{'rating': -1}")
    List<Product> findAllByNameIsNotNull(PageRequest pageRequest);
    @Query(sort = "{'rating': -1}")
    List<Product> findAllByCategory_Id(String categoryId, PageRequest pageRequest);
    @Query(sort = "{'discount': -1}")
    List<Product> findAllByQuantityIsNotNull(PageRequest pageRequest);
}
