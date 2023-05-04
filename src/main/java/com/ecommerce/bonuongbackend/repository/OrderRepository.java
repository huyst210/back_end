package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    @Query(sort = "{'createdAt': -1}")
    List<Order> findAllByUser_Id(String userId);
}
