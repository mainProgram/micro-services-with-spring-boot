package com.fazeyna.dtos.profil;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilRequest {

    private String libelle;

    private String description;

    List<Integer> habilitations;

}
