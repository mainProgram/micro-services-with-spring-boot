package com.fazeyna.habilitation;

import com.fazeyna.enumerations.Statut;
import com.fazeyna.profil.ProfilEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "habilitation")
@Entity
public class HabilitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToMany(mappedBy = "habilitations")
    List<ProfilEntity> profils = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.statut = Statut.ACTIVE;
    }
}
