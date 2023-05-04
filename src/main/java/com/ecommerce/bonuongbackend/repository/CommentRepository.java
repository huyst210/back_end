package com.ecommerce.bonuongbackend.repository;

import com.ecommerce.bonuongbackend.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(sort = "{'createdAt': -1}")
    List<Comment> findAllByProduct_Id(String productId);
}
