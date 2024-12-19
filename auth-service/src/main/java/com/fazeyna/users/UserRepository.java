package com.fazeyna.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByMatricule(String mat);

    @Query("SELECT u FROM UserEntity u WHERE u.profil.id = :profilId")
    List<UserEntity> findAllByProfil(Long profilId);

    List<UserEntity> findAllByOrderByIdDesc(Pageable pageable);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);
}


