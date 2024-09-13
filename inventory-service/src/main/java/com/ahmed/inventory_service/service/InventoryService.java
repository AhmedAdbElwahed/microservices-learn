package com.ahmed.inventory_service.service;


import com.ahmed.inventory_service.dto.InventoryResponseDto;
import com.ahmed.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponseDto> isInStock(List<String> suckCodes) {
        return inventoryRepository.findBySkuCodeIn(suckCodes).stream()
                .map((inventory) -> InventoryResponseDto.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
                )
                .toList();
    }
}
