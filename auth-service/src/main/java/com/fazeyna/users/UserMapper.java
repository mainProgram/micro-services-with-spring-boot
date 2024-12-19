package com.fazeyna.users;

import com.fazeyna.auth.RegisterRequest;
import com.fazeyna.dtos.user.UserRequest;
import com.fazeyna.dtos.user.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity mapRequestToEntity(UserRequest request){
        UserEntity user = UserEntity.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .matricule(request.getMatricule().equals("") ? null :  request.getMatricule())
                .build();
        return user;
    }

    public UserEntity mapRequestToEntity(RegisterRequest request){
        UserEntity user = UserEntity.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .matricule(request.getMatricule().equals("") ? null :  request.getMatricule())
                .build();
        return user;
    }

    public UserResponse mapEntityToResponse(UserEntity user){
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .isNew(user.isNew())
                .idProfil(user.getProfil().getId())
                .matricule(user.getMatricule())
                .statut(user.getStatut())
                .build();
        return response;
    }

    public List<UserResponse> mapEntitysToResponses(List<UserEntity> users){
        return  users.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

}
