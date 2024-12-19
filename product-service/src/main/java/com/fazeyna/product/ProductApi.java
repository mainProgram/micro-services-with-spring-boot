package com.fazeyna.product;

import com.fazeyna.dtos.user.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/product")
public interface ProductApi {

    @GetMapping("/user/{userId}")
    Mono<UserResponse> get(@PathVariable Long userId);

    @GetMapping("/users")
    Flux<UserResponse> getUsers();
}
