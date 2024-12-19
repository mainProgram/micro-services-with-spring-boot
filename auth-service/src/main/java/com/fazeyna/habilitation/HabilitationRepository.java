package com.fazeyna.habilitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabilitationRepository extends JpaRepository<HabilitationEntity, Long> {

    @Query("SELECT DISTINCT habilitation FROM HabilitationEntity habilitation JOIN habilitation.profils profil WHERE profil.id = :profilId")
    List<HabilitationEntity> findActionsByProfilId(@Param("profilId") Long id);

    Optional<HabilitationEntity> findByLibelle(String libelle);
}
