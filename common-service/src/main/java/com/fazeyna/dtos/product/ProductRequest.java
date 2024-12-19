package com.fazeyna.dtos.product;

import com.fazeyna.enumerations.Statut;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String libelle;

    private Long idCreateur;

}
