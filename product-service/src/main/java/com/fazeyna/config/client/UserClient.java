package com.fazeyna.config.client;

import com.fazeyna.dtos.user.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@HttpExchange
public interface UserClient {

    @GetExchange("api/user/details/{id}")
    Mono<UserResponse> getDetailsById(@PathVariable Long id);

    @GetExchange("api/user/sp")
    Flux<UserResponse> getUsers();
}
