package com.fazeyna.habilitation;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.habilitation.HabilitationRequest;
import com.fazeyna.dtos.habilitation.HabilitationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HabilitationController implements HabilitationApi{

    private final HabilitationService service;

    @Override
    public ResponseEntity<HabilitationResponse> add(HabilitationRequest request) {

        try {
            HabilitationResponse response = service.add(request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.CREATED, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<HabilitationResponse>> getAll() {
        try{
            List<HabilitationResponse> response = service.getAll();
            if(response.isEmpty())
                return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.NO_CONTENT, response);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        }
        catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
