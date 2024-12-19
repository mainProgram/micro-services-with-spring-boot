package com.fazeyna.dtos.user;

import com.fazeyna.enumerations.Statut;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String prenom;

    private String nom;

    private String telephone;

    private String email;

    private String matricule;

    private Statut statut;

    private String password;

    private String confirmPassword;

    private Long idProfil;

}
