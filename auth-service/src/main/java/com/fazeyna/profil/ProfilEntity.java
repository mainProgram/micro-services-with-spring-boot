package com.fazeyna.profil;

import com.fazeyna.enumerations.Statut;
import com.fazeyna.habilitation.HabilitationEntity;
import com.fazeyna.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Table(name = "profil")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String libelle;

    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @PrePersist
    public void prePersist() {
        this.statut = Statut.ACTIVE;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profil_habilitation",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "habilitation_id"))
    List<HabilitationEntity> habilitations = new ArrayList<>();

    @OneToMany(mappedBy = "profil")
    List<UserEntity> users = new ArrayList<>();
}
