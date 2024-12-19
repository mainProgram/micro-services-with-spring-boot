package com.fazeyna.profil;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.profil.ProfilRequest;
import com.fazeyna.dtos.profil.ProfilResponse;
import com.fazeyna.enumerations.Statut;
import com.fazeyna.exceptions.EntityNotFoundException;
import com.fazeyna.habilitation.HabilitationEntity;
import com.fazeyna.habilitation.HabilitationMapper;
import com.fazeyna.habilitation.HabilitationRepository;
import com.fazeyna.users.UserRepository;
import com.fazeyna.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilServiceImpl implements ProfilService{

    private final ProfilMapper profilMapper;
    private final ProfilRepository profilRepository;
    private final HabilitationMapper habilitationMapper;
    private final HabilitationRepository habilitationRepository;
    private final UserRepository userRepository;


    public ProfilEntity loadEntity(Long id) {

        return profilRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Le profil avec l'id " + id + " n'existe pas")
        );
    }

    @Transactional
    public ProfilResponse add(ProfilRequest request){

        validateAddRequest(request);

       List<HabilitationEntity> habilitations = request.getHabilitations()
               .stream()
               .map(idHabilitation -> habilitationRepository.findById(Long.valueOf(idHabilitation))
                       .orElse(null))
               .collect(Collectors.toList());

       ProfilEntity profil = profilMapper.mapRequestToEntity(request);
       profil.setHabilitations(habilitations);

       return profilMapper.mapEntityToResponse(profilRepository.save(profil));
    }

    public void validateAddRequest(ProfilRequest request){
        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getLibelle(), "Le libellé");
        CustomValidator.requireNonBlank(request.getDescription(), "La description");
        CustomValidator.requireNonEmptyArray(request.getHabilitations(), "une habilitation");
        if(profilRepository.findByLibelle(request.getLibelle()).isPresent()){
            CustomValidator.alreadyExists("Ce profil");
        }
    }

    public void validateUpdateRequest(ProfilRequest request, Long id){
        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getLibelle(), "Le libellé");
        CustomValidator.requireNonBlank(request.getDescription(), "La description");
        CustomValidator.requireNonEmptyArray(request.getHabilitations(), "une habilitation");
        if (profilRepository.findByLibelle(request.getLibelle())
                .filter(profil -> !profil.getId().equals(id))
                .isPresent()) {
            CustomValidator.alreadyExists("Ce profil");
        }

    }

    public List<ProfilResponse> getAll(){

       List<ProfilResponse> profilResponses = profilMapper.mapEntitysToResponses(profilRepository.findAllByOrderByIdDesc());
       profilResponses.stream().map(profilResponse -> {
           profilResponse.setHabilitations(habilitationMapper.mapEntitysToResponses(habilitationRepository.findActionsByProfilId(profilResponse.getId())));
           return profilResponse;
               }
       ).collect(Collectors.toList());

       return profilResponses;
    }

    public ProfilResponse getById(Long id){
       ProfilResponse profilResponse = profilMapper.mapEntityToResponse(profilRepository.findById(id).orElseThrow(
                        ()->new EntityNotFoundException("Le profil avec l'id " + id + " n'existe pas")
                )
        );
       profilResponse.setHabilitations(habilitationMapper.mapEntitysToResponses(habilitationRepository.findActionsByProfilId(id)));

       return profilResponse;
    }

    public ProfilResponse update(Long id, ProfilRequest request){

        validateUpdateRequest(request, id);

        ProfilEntity profil = loadEntity(id);
        List<HabilitationEntity> habilitations = request.getHabilitations().stream().map(idHabilitation -> habilitationRepository.findById(Long.valueOf(idHabilitation)).orElse(null)).collect(Collectors.toList());
        profil.setLibelle(request.getLibelle());
        profil.setDescription(request.getDescription());
        profil.setHabilitations(habilitations);
        ProfilResponse profilResponse = profilMapper.mapEntityToResponse(profilRepository.save(profil));
        profilResponse.setHabilitations(habilitationMapper.mapEntitysToResponses(habilitationRepository.findActionsByProfilId(profilResponse.getId())));

        return profilResponse;
    }


    public ResponseEntity<Object> delete(Long id){

        profilRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Le profil avec l'id " + id + " n'existe pas")
        );
        if(!userRepository.findAllByProfil(id).isEmpty()){
            ResponseEntity<Object> response = ResponseHandler.generateErrorResponse("Ce profil appartient à au moins un utilisateur !", HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        try {
            profilRepository.deleteById(id);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse("Erreur lors de la suppression du profil.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ProfilResponse> getProfilsByIdHabilitation(Long idHabilitation){
        return profilMapper.mapEntitysToResponses(profilRepository.findProfilsByActionId(idHabilitation));
    }

}
