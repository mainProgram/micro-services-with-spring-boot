package com.fazeyna.profil;

import com.fazeyna.dtos.profil.ProfilRequest;
import com.fazeyna.dtos.profil.ProfilResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfilService {

    public ProfilEntity loadEntity(Long id);

    @Transactional
    public ProfilResponse add(ProfilRequest request);

    public void validateAddRequest(ProfilRequest request);

    public void validateUpdateRequest(ProfilRequest request, Long id);

    public List<ProfilResponse> getAll();

    public ProfilResponse getById(Long id);

    public ProfilResponse update(Long id, ProfilRequest request);

    public ResponseEntity<Object> delete(Long id);

    public List<ProfilResponse> getProfilsByIdHabilitation(Long idHabilitation);

}
