package com.fazeyna.config.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static <T> ResponseEntity<T> generateSuccessResponse(String message, HttpStatus status, T responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return ResponseEntity.status(status).body((T) map);
    }

    public static <T> ResponseEntity<T> generateErrorResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());

        return ResponseEntity.status(status).body((T) map);
    }
}
