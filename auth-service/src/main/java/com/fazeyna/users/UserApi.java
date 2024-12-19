package com.fazeyna.users;

import com.fazeyna.dtos.filter.FilterDTO;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.enumerations.Statut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fazeyna.dtos.user.UserRequest;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
public interface UserApi {

    @PostMapping
    ResponseEntity<UserResponse> add(@RequestBody UserRequest request);

    @GetMapping
    ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    );

    @GetMapping("/sp")
    List<UserResponse> getAll();

    @PostMapping("/filter")
    ResponseEntity<Map<String, Object>> getUsersByFilter(
            @RequestBody List<FilterDTO> filterDTOList,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    );

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getById(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<UserResponse> deleteById(@PathVariable Long id);

    @PutMapping("/{id}/status")
    ResponseEntity<UserResponse> changeStatus(@PathVariable Long id, @RequestParam Statut status);

    @GetMapping("/details/{id}")
    UserResponse getDetailsById(@PathVariable Long id);
}
