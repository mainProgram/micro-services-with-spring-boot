package com.fazeyna.dtos.product;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String libelle;

    private Long idCreateur;

}
