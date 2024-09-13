package com.ahmed.product_service.service;


import com.ahmed.product_service.dto.ProductRequestDto;
import com.ahmed.product_service.dto.ProductResponseDto;
import com.ahmed.product_service.model.Product;
import com.ahmed.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto productRequest) {
        var product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product is created {}", product.getId());
    }

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map((product) -> ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

}
