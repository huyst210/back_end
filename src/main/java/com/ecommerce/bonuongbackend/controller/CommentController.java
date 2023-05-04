package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.category.CreateCategoryDto;
import com.ecommerce.bonuongbackend.dto.category.CreateCategoryResponseDto;
import com.ecommerce.bonuongbackend.dto.comment.CreateCommentDto;
import com.ecommerce.bonuongbackend.dto.comment.CreateCommentResponseDto;
import com.ecommerce.bonuongbackend.dto.comment.GetCommentsResponseDto;
import com.ecommerce.bonuongbackend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{productId}")
    public GetCommentsResponseDto getProductComments(@PathVariable String productId) {
        return commentService.getProductComments(productId);
    }

    @PostMapping("")
    public CreateCommentResponseDto createComment(@RequestBody CreateCommentDto createCommentDto) {
        return commentService.createComment(createCommentDto);
    }

    @GetMapping("")
    public GetCommentsResponseDto getAllComments() {
        return commentService.getAllComments();
    }

    @PutMapping("/{id}")
    public CreateCommentResponseDto updateComment(@PathVariable String id, @RequestBody CreateCommentDto updateCommentDto) {
        return commentService.updateComment(id, updateCommentDto);
    }

    @DeleteMapping("/{id}")
    public CreateCommentResponseDto deleteComment(@PathVariable String id) {
        return commentService.deleteComment(id);
    }
}
