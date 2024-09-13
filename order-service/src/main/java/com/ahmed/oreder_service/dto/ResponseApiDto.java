package com.ahmed.oreder_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApiDto {

    private String message;
    private HttpStatus status;
    private LocalTime timestamp;

}
