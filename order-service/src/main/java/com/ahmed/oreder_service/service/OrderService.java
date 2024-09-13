package com.ahmed.oreder_service.service;

import com.ahmed.oreder_service.dto.InventoryResponseDto;
import com.ahmed.oreder_service.dto.OrderItemDto;
import com.ahmed.oreder_service.dto.OrderRequestDto;
import com.ahmed.oreder_service.model.Order;
import com.ahmed.oreder_service.model.OrderItem;
import com.ahmed.oreder_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequestDto orderRequestDto) {

        var orderItems = orderRequestDto.getOrderItemDtoList()
                .stream()
                .map((orderItemDto -> OrderItem.builder()
                        .skuCode(orderItemDto.getSkuCode())
                        .price(orderItemDto.getPrice())
                        .quantity(orderItemDto.getQuantity())
                        .id(orderItemDto.getId())
                        .build()))
                .toList();

        var order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItems(orderItems)
                .build();

        List<String> skuCodes = orderRequestDto.getOrderItemDtoList()
                .stream()
                .map(OrderItemDto::getSkuCode)
                .toList();

        // call inventory service, place the order if the product is in stock
        InventoryResponseDto[] inventoryResponseDtos = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", skuCodes)
                                .build()
                )
                .retrieve()
                .bodyToMono(InventoryResponseDto[].class)
                .block();

        assert inventoryResponseDtos != null;
        Boolean isAllInStock = Arrays.stream(inventoryResponseDtos).allMatch(InventoryResponseDto::isInStock);


        if (Boolean.TRUE.equals(isAllInStock)) {
            orderRepository.save(order);
            log.info("order created with number {}", order.getOrderNumber());
        } else {
            throw new IllegalArgumentException("Products is not in stock");
        }


    }
}
