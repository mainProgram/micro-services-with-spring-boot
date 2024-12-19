package com.fazeyna.profil;

import com.fazeyna.dtos.profil.ProfilRequest;
import com.fazeyna.dtos.profil.ProfilResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfilMapper {
    public ProfilEntity mapRequestToEntity(ProfilRequest request){

        ProfilEntity profil = ProfilEntity.builder()
                .libelle(request.getLibelle())
                .description(request.getDescription())
                .build();
        return profil;
    }

    public ProfilResponse mapEntityToResponse(ProfilEntity profil){
        ProfilResponse response = ProfilResponse.builder()
                .id(profil.getId())
                .libelle(profil.getLibelle())
                .description(profil.getDescription())
                .statut(profil.getStatut().toString())
                .build();
        return response;
    }

    public ProfilEntity mapResponseToEntity(ProfilResponse profilResponse){
        ProfilEntity response = ProfilEntity.builder()
                .id(profilResponse.getId())
                .libelle(profilResponse.getLibelle())
                .description(profilResponse.getDescription())
                .build();
        return response;
    }

    public List<ProfilResponse> mapEntitysToResponses(List<ProfilEntity> profils){
        return   profils.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

}
