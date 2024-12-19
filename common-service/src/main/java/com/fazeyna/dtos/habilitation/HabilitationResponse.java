package com.fazeyna.dtos.habilitation;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabilitationResponse {

    private Long id;

    private String libelle;

    private String description;

    private String statut;
}
