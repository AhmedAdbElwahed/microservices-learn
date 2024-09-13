package com.ahmed.product_service.controller;


import com.ahmed.product_service.dto.ProductRequestDto;
import com.ahmed.product_service.dto.ProductResponseDto;
import com.ahmed.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDto productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        var products = productService.getProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
