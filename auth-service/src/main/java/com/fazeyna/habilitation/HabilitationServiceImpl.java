package com.fazeyna.habilitation;

import com.fazeyna.dtos.habilitation.HabilitationRequest;
import com.fazeyna.dtos.habilitation.HabilitationResponse;
import com.fazeyna.enumerations.Statut;
import com.fazeyna.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabilitationServiceImpl implements HabilitationService{

    private final HabilitationRepository repository;

    private final HabilitationMapper mapper;

    @Override
    public HabilitationResponse add(HabilitationRequest request)
    {
        validateAddRequest(request);

        HabilitationEntity habilitation = mapper.mapRequestToEntity(request);

        return mapper.mapEntityToResponse(repository.save(habilitation));
    }

    @Override
    public List<HabilitationResponse> getAll(){
        return mapper.mapEntitysToResponses(repository.findAll());
    }

    public void validateAddRequest(HabilitationRequest request){
        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getLibelle(), "Le libellé");
        CustomValidator.requireNonBlank(request.getDescription(), "La description");
        if(repository.findByLibelle(request.getLibelle()).isPresent()){
            CustomValidator.alreadyExists("Cette habilitation");
        }
    }
}
