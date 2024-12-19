package com.fazeyna.users;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.filter.FilterDTO;
import com.fazeyna.dtos.password.ChangePasswordDto;
import com.fazeyna.dtos.user.UserRequest;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.enumerations.Statut;
import com.fazeyna.exceptions.EntityNotFoundException;
import com.fazeyna.profil.ProfilEntity;
import com.fazeyna.profil.ProfilMapper;
import com.fazeyna.profil.ProfilRepository;
import com.fazeyna.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public static final String REGEX_EMAIL = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";


    public UserResponse add(UserRequest request){

        validateAddRequest(request);

        UserEntity user = userMapper.mapRequestToEntity(request);

        ProfilEntity profil = profilRepository.findById(request.getIdProfil()).orElseThrow(
                () -> new EntityNotFoundException("Ce profil n'existe pas !")
        );
        user.setProfil(profil);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(user);
        return userMapper.mapEntityToResponse(savedUser);
    }

    public UserResponse getById(Long id){
        return userMapper.mapEntityToResponse(userRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Utilisateur non trouvé !")
                )
        );
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Utilisateur non trouvé !")
        );
    }

    public UserEntity findByMail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                ()->new EntityNotFoundException("Utilisateur non trouvé !")
        );
    }

    public ResponseEntity<UserResponse> delete(Long id){
        findById(id);
        try{
            userRepository.deleteById(id);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse("Erreur lors de la suppression du profil.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserResponse update(Long id, UserRequest request){

        validateUpdateRequest(request, id);

        UserEntity user = findById(id);
        user.setPrenom(request.getPrenom());
        user.setNom(request.getNom());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setMatricule(request.getMatricule());
        user.setProfil(profilRepository.findById(request.getIdProfil()).orElseThrow(
                () -> new EntityNotFoundException("Ce profil n'existe pas !")
        ));

        return userMapper.mapEntityToResponse(userRepository.save(user));
    }

    public ResponseEntity<UserResponse> changeStatus(Long id, Statut status){
        UserEntity user = findById(id);
        user.setStatut(status);

        try {
            userRepository.save(user);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserResponse changePasswordWithoutOld(Long id, ChangePasswordDto changePasswordDto) {

        UserEntity userEntity = findById(id);

        CustomValidator.requireNonBlank(changePasswordDto.newPassword(), "Le mot de passe");
        CustomValidator.validatePassword(changePasswordDto.newPassword());
        CustomValidator.equalValues(changePasswordDto.newPassword(), changePasswordDto.confirmPassword());

        userEntity.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userEntity.setNew(false);

        return userMapper.mapEntityToResponse(userRepository.save(userEntity));
    }

    public void validateAddRequest(UserRequest request){

        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getPrenom(), "Le prénom");
        CustomValidator.requireNonBlank(request.getNom(), "Le nom");
        CustomValidator.requireNonBlank(request.getTelephone(), "Le téléphone");
        CustomValidator.requireNonBlank(request.getIdProfil(), "Le profil");
        CustomValidator.requireNonBlank(request.getEmail(), "L'adresse email");
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            CustomValidator.alreadyExists("Cette adresse email");
        }
        CustomValidator.requireNonBlank(request.getMatricule(), "Le matricule");
        if(userRepository.findByMatricule(request.getMatricule()).isPresent()){
            CustomValidator.alreadyExists("Ce matricule");
        }
        CustomValidator.requireNonBlank(request.getPassword(), "Le mot de passe");
        CustomValidator.validatePassword(request.getPassword());
        CustomValidator.requireNonBlank(request.getConfirmPassword(), "La confirmation du mot de passe");
        CustomValidator.equalValues(request.getPassword(), request.getConfirmPassword());


    }

    public void validateUpdateRequest(UserRequest request, Long id){

        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getPrenom(), "Le prénom");
        CustomValidator.requireNonBlank(request.getNom(), "Le nom");
        CustomValidator.requireNonBlank(request.getTelephone(), "Le téléphone");
        CustomValidator.requireNonBlank(request.getIdProfil(), "Le profil");
        CustomValidator.requireNonBlank(request.getMatricule(), "Le matricule");
        CustomValidator.requireNonBlank(request.getEmail(), "L'adresse email");
        if(userRepository.findByEmail(request.getEmail())
                .filter(user -> !user.getId().equals(id))
                .isPresent()) {
                CustomValidator.alreadyExists("Cette adresse email");
        }
        if(userRepository.findByMatricule(request.getMatricule())
                .filter(user -> !user.getId().equals(id))
                .isPresent()) {
                CustomValidator.alreadyExists("Ce matricule");
        }
    }

    public UserResponse save( UserEntity user){
        return userMapper.mapEntityToResponse(userRepository.save(user));
    }

    public UserResponse mapEntityToResponse(UserEntity user){
        return  userMapper.mapEntityToResponse(user);
    }

    public Map<String, Object> getUsersByFilter(List<FilterDTO> filterDTOList, Integer page, Integer size, String[] sort){

        List<Order> orders = getOrdersFromSort(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<UserEntity> users = new ArrayList<UserEntity>();

        Page pages = (userRepository.findAll(UserSpecification.columnEqual(filterDTOList), pageable));
        users = pages.getContent();
        List<UserResponse> userResponses = userMapper.mapEntitysToResponses(users);
        Map<String, Object> response = generateResponse(pages, userResponses);

        return response;
    }

    public Map<String, Object> getAll(Integer page, Integer size, String[] sort)
    {
        List<Order> orders = getOrdersFromSort(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<UserEntity> users = new ArrayList<UserEntity>();

        Page pages = (userRepository.findAll(pageable));
        users = pages.getContent();
        List<UserResponse> userResponses =  userMapper.mapEntitysToResponses(users);
        userResponses.stream().map(userResponse ->{
            if (userResponse.getIdProfil() != null){
                userResponse.setProfilResponse(profilMapper.mapEntityToResponse(profilRepository.findById(userResponse.getIdProfil()).get()));
            }
            return userResponse;
        }).collect(Collectors.toList());

        Map<String, Object> response = generateResponse(pages, userResponses);
        return response;
    }

    private Sort.Direction getSortDirection(String direction) {
        return "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }


    private List<Order> getOrdersFromSort(String[] sort) {
        List<Order> orders = new ArrayList<>();
        if (sort != null && sort.length > 0) {
            for (String sortOrder : sort) {
                if (sortOrder.contains(",")) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                } else {
                    orders.add(new Order(getSortDirection(sort[1]), sort[0]));
                }
            }
        }
        return orders;
    }

    public <T> Map<String, Object> generateResponse(Page<T> pages, List<T> data) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return response;
    }

    public List<UserResponse> getAll(){
        return userMapper.mapEntitysToResponses(userRepository.findAll());
    }

}
