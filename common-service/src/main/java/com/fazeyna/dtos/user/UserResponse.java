package com.fazeyna.dtos.user;

import com.fazeyna.enumerations.Statut;
import com.fazeyna.dtos.profil.ProfilResponse;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String prenom;

    private String nom;

    private String telephone;

    private String email;

    private boolean isNew;

    private String matricule;

    private Long idProfil;

    private Statut statut;

    private ProfilResponse profilResponse;

}
