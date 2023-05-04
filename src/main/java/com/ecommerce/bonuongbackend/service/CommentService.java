package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.comment.CreateCommentDto;
import com.ecommerce.bonuongbackend.dto.comment.CreateCommentResponseDto;
import com.ecommerce.bonuongbackend.dto.comment.GetCommentsResponseDto;
import com.ecommerce.bonuongbackend.model.Comment;
import com.ecommerce.bonuongbackend.model.Product;
import com.ecommerce.bonuongbackend.model.User;
import com.ecommerce.bonuongbackend.repository.CommentRepository;
import com.ecommerce.bonuongbackend.repository.ProductRepository;
import com.ecommerce.bonuongbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public GetCommentsResponseDto getProductComments(String productId) {
        try {
            List<Comment> comments = commentRepository.findAllByProduct_Id(productId);
            return new GetCommentsResponseDto(200,true,"Lấy dữ liệu thành công",comments);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetCommentsResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCommentResponseDto createComment(CreateCommentDto createCommentDto) {
        try {
            Product product = productRepository.findById(createCommentDto.getProductId()).get();
            if(product == null)
                return new CreateCommentResponseDto(404,false,"Không tìm thấy sản phẩm");
            User user = userRepository.findById(createCommentDto.getUserId()).get();
            if(user == null)
                return new CreateCommentResponseDto(404,false,"Không tìm thấy tài khoản");
            Comment comment = new Comment(
                    createCommentDto.getContent(),
                    user,
                    product,
                    createCommentDto.getRating()
            );
            commentRepository.insert(comment);

            List<Comment> comments = commentRepository.findAllByProduct_Id(product.getId());
            AtomicInteger sum = new AtomicInteger();
            comments.forEach(item -> sum.addAndGet(item.getRating()));
            product.setRating(sum.get()/comments.size());
            productRepository.save(product);
            return new CreateCommentResponseDto(200,true,"Tạo bình luận thành công", comment);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCommentResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetCommentsResponseDto getAllComments() {
        try {
            List<Comment> comments = commentRepository.findAll();
            return new GetCommentsResponseDto(200,true,"Lấy dữ liệu thành công",comments);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetCommentsResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCommentResponseDto updateComment(String id, CreateCommentDto updateCommentDto) {
        try {
            Comment comment = commentRepository.findById(id).get();
            if(comment == null)
                return new CreateCommentResponseDto(404,false,"Không tìm thấy bình luận");
            comment.setContent(updateCommentDto.getContent());
            comment.setRating(updateCommentDto.getRating());
            commentRepository.save(comment);

            List<Comment> comments = commentRepository.findAllByProduct_Id(comment.getProduct().getId());
            AtomicInteger sum = new AtomicInteger();
            comments.forEach(item -> sum.addAndGet(item.getRating()));

            Product product = productRepository.findById(updateCommentDto.getProductId()).get();
            if(product == null)
                return new CreateCommentResponseDto(404,false,"Không tìm thấy sản phẩm");

            product.setRating(sum.get()/comments.size());
            productRepository.save(product);

            return new CreateCommentResponseDto(200,true,"Sửa bình luận thành công", comment);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCommentResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCommentResponseDto deleteComment(String id) {
        try {
            Comment comment = commentRepository.findById(id).get();
            if(comment == null)
                return new CreateCommentResponseDto(404,false,"Không tìm thấy bình luận");
            commentRepository.delete(comment);
            return new CreateCommentResponseDto(200,true,"Xoá bình luận thành công", comment);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCommentResponseDto(500,false,"Lỗi Server");
        }
    }
}
