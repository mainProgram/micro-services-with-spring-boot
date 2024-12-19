package com.fazeyna.profil;

import com.fazeyna.dtos.profil.ProfilRequest;
import com.fazeyna.dtos.profil.ProfilResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/profil")
public interface ProfilApi {

    @PostMapping
    ResponseEntity<ProfilResponse> add(@RequestBody ProfilRequest request);

    @GetMapping
    ResponseEntity<List<ProfilResponse>> getAll() ;

    @GetMapping("/{id}")
    ResponseEntity<ProfilResponse> getById(@PathVariable Long id);

    @GetMapping("/habilitation/{id}")
    ResponseEntity<List<ProfilResponse>> getByIdHabilitation(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<ProfilResponse> update(@PathVariable Long id, @RequestBody ProfilRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteById(@PathVariable Long id);

}
