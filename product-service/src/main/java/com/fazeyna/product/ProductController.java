package com.fazeyna.product;

import com.fazeyna.dtos.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi{

    private final ProductService service;

    public Mono<UserResponse> get(Long userId) {
        return service.getUser(userId);
    }

    public Flux<UserResponse> getUsers(){
        return service.getUsers();
    }

}
