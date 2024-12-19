package com.fazeyna.models;

import com.fazeyna.dtos.profil.ProfilResponse;
import com.fazeyna.enumerations.Statut;
public record UserDto (Long id, String prenom, String nom, String telephone, String email, boolean isNew, String matricule, Long idProfil, Statut statut) { }
