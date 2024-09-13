package com.ahmed.inventory_service.controller;


import com.ahmed.inventory_service.dto.InventoryResponseDto;
import com.ahmed.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    public ResponseEntity<List<InventoryResponseDto>> isInStock(@RequestParam List<String> skuCode) {
        var inventoryDtos = inventoryService.isInStock(skuCode);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(inventoryDtos);
    }
}
