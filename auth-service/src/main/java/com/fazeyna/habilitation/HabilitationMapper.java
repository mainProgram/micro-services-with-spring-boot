package com.fazeyna.habilitation;

import com.fazeyna.dtos.habilitation.HabilitationRequest;
import com.fazeyna.dtos.habilitation.HabilitationResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HabilitationMapper {
    public HabilitationEntity mapRequestToEntity(HabilitationRequest request)
    {
        HabilitationEntity habilitation = HabilitationEntity.builder()
                .libelle(request.getLibelle())
                .description(request.getDescription())
                .build();
        return habilitation;
    }

    public HabilitationResponse mapEntityToResponse(HabilitationEntity action){
        HabilitationResponse response = HabilitationResponse.builder()
                .id(action.getId())
                .libelle(action.getLibelle())
                .description(action.getDescription())
                .statut(action.getStatut().toString())
                .build();
        return response;
    }
    public HabilitationEntity mapResponseToEntity(HabilitationResponse response){
        HabilitationEntity entity = HabilitationEntity.builder()
                .id(response.getId())
                .libelle(response.getLibelle())
                .description(response.getDescription())
                .build();
        return entity;
    }

    public List<HabilitationResponse> mapEntitysToResponses(List<HabilitationEntity> habilitations){
        return  habilitations.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    public List<HabilitationEntity> mapResponsesToEntity(List<HabilitationResponse> responses){
        return  responses.stream().map(this::mapResponseToEntity).collect(Collectors.toList());
    }
}
