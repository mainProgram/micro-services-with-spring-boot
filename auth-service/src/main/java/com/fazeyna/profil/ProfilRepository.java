package com.fazeyna.profil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfilRepository extends JpaRepository<ProfilEntity,Long> {
  List<ProfilEntity> findAllByOrderByIdDesc();

  Optional<ProfilEntity>  findByLibelle(String profil);

  @Query("SELECT DISTINCT profil FROM ProfilEntity profil JOIN profil.habilitations habilitation WHERE habilitation.id = :habilitationId")
  List<ProfilEntity> findProfilsByActionId(@Param("habilitationId") Long id);

}
