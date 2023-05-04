package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.product.CreateProductDto;
import com.ecommerce.bonuongbackend.dto.product.CreateProductResponseDto;
import com.ecommerce.bonuongbackend.dto.product.GetProductsResponseDto;
import com.ecommerce.bonuongbackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public GetProductsResponseDto getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/latest")
    public GetProductsResponseDto getLatestProducts() {
        return productService.getLatestProducts();
    }

    @GetMapping("/top-rating")
    public GetProductsResponseDto getTopRatingProducts() {
        return productService.getTopRatingProducts();
    }

    @GetMapping("/related/{id}")
    public GetProductsResponseDto getRelatedProducts(@PathVariable String id) {
        return productService.getRelatedProducts(id);
    }

    @GetMapping("/discount")
    public GetProductsResponseDto getDiscountProducts() {
        return productService.getDiscountProducts();
    }

    @GetMapping("/{id}")
    public CreateProductResponseDto getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @PostMapping("")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductDto createProductDto) {
        return productService.createProduct(createProductDto);
    }

    @PutMapping("/{id}")
    public CreateProductResponseDto updateProduct(@PathVariable String id, @RequestBody CreateProductDto updateProductDto) {
        return productService.updateProduct(id, updateProductDto);
    }

    @DeleteMapping("/{id}")
    public CreateProductResponseDto deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
