package com.fazeyna.users;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.filter.FilterDTO;
import com.fazeyna.dtos.user.UserRequest;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.enumerations.Statut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public ResponseEntity<UserResponse> add(UserRequest request) {
        try {
            UserResponse response = service.add(request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAll(Integer page, Integer size, String[] sort) {

        try{
            Map<String, Object> response = service.getAll(page, size, sort);
            if(response.isEmpty())
                return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.NO_CONTENT, response);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        }
        catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserResponse> getById(Long id) {

        try {
            UserResponse response = service.getById(id);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse getDetailsById(Long id) {
        return service.getById(id);
    }

    @Override
    public ResponseEntity<UserResponse> update(Long id, UserRequest request) {
        try {
            UserResponse response = service.update(id, request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserResponse> deleteById(Long id) {
        return service.delete(id);
    }

    @Override
    public ResponseEntity<UserResponse> changeStatus(Long id, Statut status) {
        return service.changeStatus(id, status);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUsersByFilter(List<FilterDTO> filterDTOList, Integer page, Integer size, String[] sort) {

        try{
            Map<String, Object> response = service.getUsersByFilter(filterDTOList, page, size, sort);
            if(response.isEmpty())
                return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.NO_CONTENT, response);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        }
        catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserResponse> getAll(){
        return service.getAll();
    }
}

