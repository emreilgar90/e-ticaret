package org.example.service;

import org.example.dto.CreateUserRequest;
import org.example.dto.UpdateUserRequest;
import org.example.dto.UserDto;
import org.example.dto.UserDtoConverter;
import org.example.exception.ErrorType;
import org.example.exception.UserManagerException;
import org.example.model.UserInformation;
import org.example.repository.UserInformationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {
    private final UserInformationRepository userInformationRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserInformationRepository userInformationRepository, UserDtoConverter userDtoConverter) {
        this.userInformationRepository = userInformationRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public List<UserDto> getAllUsers() {
        return userInformationRepository.findAll().stream().map(userDtoConverter::convert).collect(Collectors.toList());
    }

    public UserDto getUserByMail(String mail) {
        UserInformation userInformation = findUserByMail(mail);
        return userDtoConverter.convert(userInformation);
    }

    public UserDto createUser(CreateUserRequest userRequest) {
        UserInformation userInformation = new UserInformation(userRequest.getMail(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getMiddleName(),
                false);
        return userDtoConverter.convert(userInformationRepository.save(userInformation));
    }

    public UserDto updateUser(String mail, UpdateUserRequest updateUserRequest) {
        UserInformation userInformation = findUserByMail(mail);
        if(!userInformation.getIsActive()){
            throw new UserManagerException(ErrorType.USER_NOT_ACTIVE);
        }
        UserInformation updatedUserInformation = new UserInformation(userInformation.getId(),userInformation.getMail(),
                updateUserRequest.getFirstName(),
                updateUserRequest.getLastName(),
                updateUserRequest.getMiddleName());
        return userDtoConverter.convert(userInformationRepository.save(updatedUserInformation));
    }
    public void deactivateUser(Long id) {
        UserInformation userInformation= findUserById(id);
        UserInformation updatedUserInformation = new UserInformation(userInformation.getId(),userInformation.getMail(),
                userInformation.getFirstName(),
                userInformation.getLastName(),
                userInformation.getMiddleName(),
                false);
        userInformationRepository.save(updatedUserInformation);

    }

    public void deleteUser(Long id) {
        if(doesUserExist(id)){
            userInformationRepository.deleteById(id);
        }else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    /********************************************************/
    private UserInformation findUserByMail(String mail) {
        return userInformationRepository.findByMail(mail)
                .orElseThrow(() ->new UserManagerException(ErrorType.NO_ACCOUNT_FOUND_FOR_EMAIL));
    }
    private UserInformation findUserById(Long id) {
        return userInformationRepository.findById(id)
                .orElseThrow(() ->new UserManagerException(ErrorType.NO_ACCOUNT_FOUND_FOR_EMAIL));
    }
    private boolean doesUserExist(Long id){
        return userInformationRepository.existsById(id);
    }
}
