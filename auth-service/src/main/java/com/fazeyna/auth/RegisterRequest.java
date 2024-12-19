package com.fazeyna.auth;

import com.fazeyna.enumerations.Statut;
import com.fazeyna.profil.ProfilEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String prenom;

    private String nom;

    private String telephone;

    private String email;

    private String matricule;

    private Long idProfil;
}
