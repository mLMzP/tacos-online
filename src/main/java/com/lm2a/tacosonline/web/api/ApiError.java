package com.lm2a.tacosonline.web.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private String path;
    private Instant instant;
}
