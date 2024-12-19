package com.fazeyna.product;

import com.fazeyna.dtos.user.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<UserResponse> getUser(Long idUser);

    Flux<UserResponse> getUsers();

}
