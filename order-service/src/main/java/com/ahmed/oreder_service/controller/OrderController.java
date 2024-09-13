package com.ahmed.oreder_service.controller;


import com.ahmed.oreder_service.dto.OrderRequestDto;
import com.ahmed.oreder_service.dto.ResponseApiDto;
import com.ahmed.oreder_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseApiDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {

        orderService.placeOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApiDto(
                "Order Created Successfully",
                HttpStatus.CREATED,
                LocalTime.now()
        ));
    }
}
