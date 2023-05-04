package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.category.CreateCategoryDto;
import com.ecommerce.bonuongbackend.dto.category.CreateCategoryResponseDto;
import com.ecommerce.bonuongbackend.dto.category.GetCategoriesResponseDto;
import com.ecommerce.bonuongbackend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public GetCategoriesResponseDto getCategories() { return categoryService.getCategories(); }

    @PostMapping("")
    public CreateCategoryResponseDto createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createCategory(createCategoryDto);
    }

    @PutMapping("/{id}")
    public CreateCategoryResponseDto updateCategory(@PathVariable String id, @RequestBody CreateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(id, updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public CreateCategoryResponseDto deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id);
    }
}
