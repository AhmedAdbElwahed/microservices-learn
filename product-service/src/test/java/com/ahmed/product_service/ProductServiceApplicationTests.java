package com.ahmed.product_service;

import com.ahmed.product_service.model.Product;
import com.ahmed.product_service.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

//	@Test
//	void contextLoads() {
//	}

    @Test
    void createProductAndReturnResponseCodeCreated() throws Exception {

        var product = getProductRequest();
        var jsonProduct = objectMapper.writeValueAsString(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void getAllProductsAndReturnsResponseCodeOk() throws Exception {
        var product = getProductRequest();
        productRepository.save(product);
        var jsonProduct = objectMapper.writeValueAsString(List.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .content()
                                .json(jsonProduct)
                );
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    private Product getProductRequest() {
        return Product.builder()
                .name("Iphone 30 Pro")
                .description("256GB Space 16GB RAM 5 inches water proof")
                .price(BigDecimal.valueOf(30000))
                .build();
    }

}
