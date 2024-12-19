package com.fazeyna.dtos.habilitation;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabilitationRequest {

    private String libelle;

    private String description;
}
