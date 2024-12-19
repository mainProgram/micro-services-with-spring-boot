package com.fazeyna.profil;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.profil.ProfilRequest;
import com.fazeyna.dtos.profil.ProfilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfilController implements ProfilApi{

    private final ProfilServiceImpl service;

    @Override
    public ResponseEntity<ProfilResponse> add(ProfilRequest request) {
        try {
            ProfilResponse response = service.add(request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public  ResponseEntity<List<ProfilResponse>> getAll() {

        try{
            List<ProfilResponse> response = service.getAll();
            if(response.isEmpty())
                return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.NO_CONTENT, response);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        }
        catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ProfilResponse> getById(Long id) {
        try {
            ProfilResponse response = service.getById(id);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ProfilResponse>> getByIdHabilitation(Long id) {
        try{
            List<ProfilResponse> response = service.getProfilsByIdHabilitation(id);
            if(response.isEmpty())
                return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.NO_CONTENT, response);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        }
        catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ProfilResponse> update(Long id, ProfilRequest request) {
        try {
            ProfilResponse response = service.update(id,request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        return service.delete(id);
    }
}
