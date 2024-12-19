package com.fazeyna.users;

import com.fazeyna.dtos.filter.FilterDTO;
import com.fazeyna.dtos.user.UserRequest;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.enumerations.Statut;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponse add(UserRequest request);

    Map<String, Object> getAll(Integer page, Integer size, String[] sort);

    UserResponse getById(Long id);

    UserEntity findById(Long id);

    UserEntity findByMail(String email);

    UserResponse save( UserEntity user);

    ResponseEntity<UserResponse> delete(Long id);

    UserResponse update(Long idUser,UserRequest userRequest);

    ResponseEntity<UserResponse> changeStatus(Long id, Statut status);

    UserResponse mapEntityToResponse(UserEntity user);

    Map<String, Object> getUsersByFilter(List<FilterDTO> filterDTOList, Integer page, Integer size, String[] sort);

    List<UserResponse> getAll();

}
