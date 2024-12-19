package com.fazeyna.product;

import com.fazeyna.config.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fazeyna.dtos.user.UserResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final UserClient userClient;

    @Override
    public Mono<UserResponse> getUser(Long userId) {
        return userClient.getDetailsById(userId);
    }

    @Override
    public Flux<UserResponse> getUsers(){
        return userClient.getUsers();
    }

}
