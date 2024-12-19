package com.fazeyna.habilitation;

import com.fazeyna.dtos.habilitation.HabilitationRequest;
import com.fazeyna.dtos.habilitation.HabilitationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/habilitation")
public interface HabilitationApi {

    @PostMapping
    ResponseEntity<HabilitationResponse> add(@RequestBody HabilitationRequest request);

    @GetMapping
    public ResponseEntity<List<HabilitationResponse>> getAll() ;
}
