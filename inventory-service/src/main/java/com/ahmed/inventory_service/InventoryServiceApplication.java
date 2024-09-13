package com.ahmed.inventory_service;

import com.ahmed.inventory_service.model.Inventory;
import com.ahmed.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository) {
        return args -> {

            var items = inventoryRepository.findAll();
            if (items.isEmpty()) {
                var item1 = Inventory.builder()
                        .skuCode("SE8G5T6")
                        .price(BigDecimal.valueOf(30000))
                        .quantity(300)
                        .build();
                inventoryRepository.save(item1);
            }
        };
    }

}
