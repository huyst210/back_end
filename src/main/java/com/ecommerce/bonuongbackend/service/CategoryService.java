package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.category.CreateCategoryDto;
import com.ecommerce.bonuongbackend.dto.category.CreateCategoryResponseDto;
import com.ecommerce.bonuongbackend.dto.category.GetCategoriesResponseDto;
import com.ecommerce.bonuongbackend.model.Category;
import com.ecommerce.bonuongbackend.model.Product;
import com.ecommerce.bonuongbackend.repository.CategoryRepository;
import com.ecommerce.bonuongbackend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public GetCategoriesResponseDto getCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new GetCategoriesResponseDto(200,true,"Lấy dữ liệu thành công",categories);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetCategoriesResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCategoryResponseDto createCategory(CreateCategoryDto createCategoryDto) {
        try {
            Category category = new Category(createCategoryDto.getName());
            categoryRepository.insert(category);
            return new CreateCategoryResponseDto(200,true,"Tạo loại sản phẩm thành công", category);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCategoryResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCategoryResponseDto updateCategory(String id, CreateCategoryDto updateCategoryDto) {
        try {
            Category category = categoryRepository.findById(id).get();
            if(category == null)
                return new CreateCategoryResponseDto(404,false,"Không tìm thấy loại sản phẩm");
            category.setName(updateCategoryDto.getName());
            categoryRepository.save(category);
            return new CreateCategoryResponseDto(200,true,"Sửa loại sản phẩm thành công", category);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCategoryResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateCategoryResponseDto deleteCategory(String id) {
        try {
            Category category = categoryRepository.findById(id).get();
            if(category == null)
                return new CreateCategoryResponseDto(404,false,"Không tìm thấy loại sản phẩm");
            Product product = productRepository.findProductByCategory(category);
            if(Objects.nonNull(product))
                return new CreateCategoryResponseDto(400,false,"Tồn tại sản phẩm, không cho xoá", category);
            categoryRepository.delete(category);
            return new CreateCategoryResponseDto(200,true,"Xoá loại sản phẩm thành công", category);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateCategoryResponseDto(500,false,"Lỗi Server");
        }
    }
}
