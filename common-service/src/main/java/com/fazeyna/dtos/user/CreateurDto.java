package com.fazeyna.dtos.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateurDto {
    private String prenom;
    private String nom;

    private String telephone;

    private String email;
}
