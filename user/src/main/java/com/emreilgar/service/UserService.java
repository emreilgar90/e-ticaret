package com.emreilgar.service;

import com.emreilgar.dto.CreateUserRequestDto;
import com.emreilgar.dto.UpdateUserRequestDto;
import com.emreilgar.dto.UserDto;
import com.emreilgar.dto.UserDtoConverter;
import com.emreilgar.exception.*;
import com.emreilgar.model.Users;

import com.emreilgar.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    private final UsersRepository usersRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UsersRepository usersRepository, UserDtoConverter userDtoConverter) {
        this.usersRepository = usersRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public List<UserDto> getAllUsers() { //1+
        return userDtoConverter.convert(usersRepository.findAll());
    }

    public UserDto getUserByMail(final String mail) {//2++
        Users users = findUserByMail(mail);
        return userDtoConverter.convert(users);
    }

    public UserDto createUser(final CreateUserRequestDto dto) {//3+
        Users users = new Users();
        users.setMail(dto.getMail());
        users.setFirstName(dto.getFirstName());
        users.setLastName(dto.getLastName());
        users.setMiddleName(dto.getMiddleName());
        users.setIsActive(false);
        return userDtoConverter.convert(usersRepository.save(users));
    }

    public UserDto updateUser(final String mail, final UpdateUserRequestDto updateUserRequest) { //4++
        try {
            Users users = findUserByMail(mail);
            if (!users.getIsActive()) {
                throw new UserIsNotActiveException(ErrorType.USER_NOT_ACTIVE, "Kullanıcı Aktif Değil !");
            }
            users.setFirstName(updateUserRequest.getFirstName());
            users.setLastName(updateUserRequest.getLastName());
            users.setMiddleName(updateUserRequest.getMiddleName());
            usersRepository.save(users);
            return userDtoConverter.convert(users);
        } catch (UserIsNotActiveException ex) {
            ex.printStackTrace(); // Hata izleme
            return null; // Veya başka bir geri dönüş yapma işlemi
        }
    }

    public Users deactivateUser(final Long id) throws UserNotFoundException{ //5++
        {
            Users users = findUserById(id);

            Users updatedUsers = new Users(
                    users.getId(),
                    users.getMail(),
                    users.getFirstName(),
                    users.getLastName(),
                    users.getMiddleName(),
                    false
            );
            usersRepository.save(updatedUsers);
            return updatedUsers;
        }
    }
    public void activateUser(final Long id) { //6++
        Users users = findUserById(id);
        users.setIsActive(true);
        usersRepository.save(users);

    }

    public void deleteUser(final Long id) { //7++
        findUserById(id);
        usersRepository.deleteById(id);

    }
    /********************************************************/
    private Users findUserByMail(final String mail) {
        return usersRepository.findByMail(mail)
                .orElseThrow(() ->new UserManagerException(ErrorType.NO_ACCOUNT_FOUND_FOR_EMAIL));
    }
    protected Users findUserById(final Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() ->new UserNotFoundException(ErrorType.NO_ACCOUNT_FOUND_FOR_ID));
    }
    private boolean doesUserExist(final Long id){
        return usersRepository.existsById(id);
    }



}
