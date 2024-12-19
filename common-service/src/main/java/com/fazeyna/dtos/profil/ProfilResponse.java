package com.fazeyna.dtos.profil;

import com.fazeyna.dtos.habilitation.HabilitationResponse;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilResponse {

    private Long id;

    private String libelle;

    private String description;

    private String statut;

    private List<HabilitationResponse> habilitations;
}
