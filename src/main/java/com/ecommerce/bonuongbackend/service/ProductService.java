package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.product.CreateProductDto;
import com.ecommerce.bonuongbackend.dto.product.CreateProductResponseDto;
import com.ecommerce.bonuongbackend.dto.product.GetProductsResponseDto;
import com.ecommerce.bonuongbackend.model.Category;
import com.ecommerce.bonuongbackend.model.Comment;
import com.ecommerce.bonuongbackend.model.Product;
import com.ecommerce.bonuongbackend.repository.CategoryRepository;
import com.ecommerce.bonuongbackend.repository.CommentRepository;
import com.ecommerce.bonuongbackend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    public GetProductsResponseDto getProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return new GetProductsResponseDto(200,true,"Lấy dữ liệu thành công", products);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetProductsResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateProductResponseDto createProduct(CreateProductDto createProductDto) {
        try {
            Category category = categoryRepository.findById(createProductDto.getCategoryId()).get();
            if(category == null)
                return new CreateProductResponseDto(404,false,"Không tìm thấy loại sản phẩm");

            Product product = new Product(
                    category,
                    createProductDto.getName(),
                    createProductDto.getDescription(),
                    createProductDto.getPrice(),
                    createProductDto.getQuantity(),
                    createProductDto.getUnit(),
                    createProductDto.getDiscount(),
                    createProductDto.getImage()
            );
            productRepository.insert(product);
            return new CreateProductResponseDto(200,true,"Tạo sản phẩm thành công", product);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateProductResponseDto(500,false,"Lỗi Server");
        }
    }
  
    public CreateProductResponseDto updateProduct(String id, CreateProductDto updateProductDto) {
        try {
            Product product = productRepository.findById(id).get();
            if(product == null)
                return new CreateProductResponseDto(404,false,"Không tìm thấy sản phẩm");
            Category category = categoryRepository.findById(updateProductDto.getCategoryId()).get();
            if(category == null)
                return new CreateProductResponseDto(404,false,"Không tìm thấy loại sản phẩm");
            product.setCategory(category);
            product.setName(updateProductDto.getName());
            product.setDescription(updateProductDto.getDescription());
            product.setPrice(updateProductDto.getPrice());
            product.setQuantity(updateProductDto.getQuantity());
            product.setUnit(updateProductDto.getUnit());
            product.setDiscount(updateProductDto.getDiscount());
            product.setImage(updateProductDto.getImage());
            productRepository.save(product);
            return new CreateProductResponseDto(200,true,"Sửa sản phẩm thành công", product);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateProductResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateProductResponseDto deleteProduct(String id) {
        try {
            Product product = productRepository.findById(id).get();
            if(product == null)
                return new CreateProductResponseDto(404,false,"Không tìm thấy sản phẩm");
            productRepository.delete(product);
            List<Comment> comments = commentRepository.findAllByProduct_Id(product.getId());
            comments.forEach(item -> commentRepository.delete(item));
            return new CreateProductResponseDto(200,true,"Xoá sản phẩm thành công", product);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateProductResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateProductResponseDto getProduct(String id) {
        try {
            Product product = productRepository.findById(id).get();
            if(product == null)
                return new CreateProductResponseDto(404,false,"Không tìm thấy sản phẩm");
            return new CreateProductResponseDto(200,true,"Lấy thông tin sản phẩm thành công", product);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateProductResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetProductsResponseDto getLatestProducts() {
        try {
            List<Product> products = productRepository.findAllByDescriptionIsNotNull(PageRequest.of(0, 3));
            return new GetProductsResponseDto(200,true,"Lấy dữ liệu thành công", products);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetProductsResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetProductsResponseDto getTopRatingProducts() {
        try {
            List<Product> products = productRepository.findAllByNameIsNotNull(PageRequest.of(0, 3));
            return new GetProductsResponseDto(200,true,"Lấy dữ liệu thành công", products);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetProductsResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetProductsResponseDto getDiscountProducts() {
        try {
            List<Product> products = productRepository.findAllByQuantityIsNotNull(PageRequest.of(0, 3));
            return new GetProductsResponseDto(200,true,"Lấy dữ liệu thành công", products);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetProductsResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetProductsResponseDto getRelatedProducts(String categoryId) {
        try {
            List<Product> products = productRepository.findAllByCategory_Id(categoryId, PageRequest.of(0, 4));
            return new GetProductsResponseDto(200,true,"Lấy dữ liệu thành công", products);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetProductsResponseDto(500,false,"Lỗi Server");
        }
    }
}
