package com.emreilgar.service;

import com.emreilgar.TestSupport;
import com.emreilgar.dto.CreateUserRequestDto;
import com.emreilgar.dto.UpdateUserRequestDto;
import com.emreilgar.dto.UserDto;
import com.emreilgar.dto.UserDtoConverter;
import com.emreilgar.exception.UserManagerException;
import com.emreilgar.exception.UserNotFoundException;
import com.emreilgar.model.Users;
import com.emreilgar.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends TestSupport {
    private UserDtoConverter converter;
    private UsersRepository repository;
    private UserService userService;
    @BeforeEach
    void setUp() {
        converter= mock(UserDtoConverter.class);
        repository= mock(UsersRepository.class);
        userService= new UserService(repository,converter);
    }
    @Test
    public void testGetAllUsers_itShouldReturnUserDtoList(){//1
        List<Users> usersList = generateUsers();
        List<UserDto> userDtoList= generateUserDtoList(usersList);

        when(repository.findAll()).thenReturn(usersList);
        when(converter.convert(usersList)).thenReturn(generateUserDtoList(usersList));

        List<UserDto> result= userService.getAllUsers();

        assertEquals(userDtoList,result);
        //verify-> Doğrulamak için
        verify(repository).findAll();//repo'nun findAll metodu çağrıldı mı ?
        verify(converter).convert(usersList);

    }

@Test
public void testGetUserByMail_whenUserMailExist_itShouldReturnUserDto() {//2+
    // Verilerin hazırlanması
    String mail = "emreilgar90@gmail.com";
    Users users = generateUser(mail);
    UserDto userDto = generateUserDto(mail);

    // Mock repository ve converter davranışlarının ayarlanması
    when(repository.findByMail(mail)).thenReturn(Optional.of(users));
    when(converter.convert(users)).thenReturn(userDto);

    // Servis çağrısının yapılması
    UserDto result = userService.getUserByMail(mail);

    // Sonucun doğrulanması
    assertEquals(userDto, result);

    // Çağrıların doğrulanması (verification)
    verify(repository).findByMail(mail); // repository'nin findByMail metodunun çağrıldığı kontrol ediliyor
    verify(converter).convert(users); // converter'ın convert metodunun çağrıldığı kontrol ediliyor
}
    @Test
    public void testGetUserByMail_whenUserMailExist_itShouldThrowUserNotFoundException() {//2++
        // Verilerin hazırlanması
        String mail = "emreilgar90@gmail.com";
        // Mock repository ve converter davranışlarının ayarlanması
      when(repository.findByMail(mail)).thenReturn(Optional.empty());

        // Sonucun doğrulanması
        assertThrows(UserManagerException.class,()->userService.getUserByMail(mail));
        // Çağrıların doğrulanması (verification)
        verify(repository).findByMail(mail); // repository'nin findByMail metodunun çağrıldığı kontrol ediliyor
        verifyNoInteractions(converter); // converter'ın convert metodunun çağrıldığı kontrol ediliyor
    }


    @Test
   public void testCreateUser_itShouldReturnCreatedUserDto() { //3+
    // Verilerin hazırlanması
    String mail = "emreilgar90@gmail.com";
    CreateUserRequestDto dto = new CreateUserRequestDto(mail, "firstName", "lastName", "middleName");
    Users users = new Users();
    users.setMail(mail);
    users.setFirstName("firstName");
    users.setLastName("lastName");
    users.setMiddleName("middleName");
    users.setIsActive(false);

    UserDto userDto = new UserDto(mail, "firstName", "lastName", "middleName");

    // Mock repository ve converter davranışlarının ayarlanması
    UsersRepository userRepository = Mockito.mock(UsersRepository.class);
    UserDtoConverter userDtoConverter = Mockito.mock(UserDtoConverter.class);

    when(userRepository.save(eq(users))).thenReturn(users);
    when(userDtoConverter.convert(eq(users))).thenReturn(userDto);

    UserService userService = new UserService(userRepository, userDtoConverter);

    // Metodun çağrılması ve sonucun kontrol edilmesi
    UserDto result = userService.createUser(dto);

    assertEquals(userDto, result);
    verify(userRepository).save(eq(users));
    verify(userDtoConverter).convert(eq(users));
}
@Test
    public void testUpdateUser_whenUserMailExistAndUserIsActive_itShouldReturnUpdateUserDto(){  // 4+ başarılı senaryo
        String mail= "emreilgar90@gmail.com";
        UpdateUserRequestDto updateUserRequest= new UpdateUserRequestDto("firstNameNew","lastNameNew","middleName");
        Users users = new Users(1L,mail,"firstName","lastName","",true);
        Users updateUsers = new Users(1L,mail,"firstNameNew","lastNameNew","middleName",true);
        Users savedUsers = new Users(1L,mail,"firstNameNew","lastNameNew","middleName",true);
        UserDto userDto= new UserDto(mail,"firstNameNew","lastNameNew","middleName");

        when(repository.findByMail(mail)).thenReturn(Optional.of(users));
        when(repository.save(updateUsers)).thenReturn(savedUsers);
        when(converter.convert(savedUsers)).thenReturn(userDto);

        UserDto result = userService.updateUser(mail,updateUserRequest);

        assertEquals(userDto,result);

        verify(repository).findByMail(mail);   //when -> verify
        verify(repository).save(updateUsers);
        verify(converter).convert(savedUsers);
}
    @Test
    public void testUpdateUser_whenUserMailDoesNotExist_itShouldReturnUpdateUserDto(){  // 4+ başarısız senaryo
        String mail= "emreilgar90@gmail.com";
        UpdateUserRequestDto updateUserRequest= new UpdateUserRequestDto("firstNameNew","lastNameNew","middleName");
        when(repository.findByMail(mail)).thenReturn(Optional.empty());


        assertThrows(UserManagerException.class, () -> userService.updateUser(mail, updateUserRequest));

        //when -> verify
        verify(repository).findByMail(mail);
        verifyNoMoreInteractions(repository); //mock nesnesi üzerinde bir kere çağırıldığını doğrular.
        verifyNoInteractions(converter); //mock nesne üzerinde hiçbir etkileşim olmadığını doğrulamak için kullanılır
    }

    @Test
    public void testDeactivateUser_whenUserIdExist_itShouldUpdateUserByActiveFalse(){  // 5+ başarılı senaryo
        String mail= "emreilgar90@gmail.com";
        Users users = new Users(userId,"emreilgar90@gmail.com","firstName","lastName","",true);
        Users deactivateUsers = new Users(userId,"emreilgar90@gmail.com","firstName","lastName","",false);
        Users savedUsers = new Users(userId,"emreilgar90@gmail.com","firstName","lastName","",false);

        when(repository.findById(userId)).thenReturn(Optional.of(users));

        // Metodun çağrılıp çağrılmadığının kontrolü
        userService.deactivateUser(userId);


        //when -> verify
        verify(repository).findById(userId);
        verify(repository).save(savedUsers); //mock nesnesi üzerinde bir kere çağırıldığını doğrular.

    }
    @Test
    public void testDeactivateUser_whenUserIdDoesExist_itShouldThrowUserNotFoundException(){  // 5++ başarısız senaryo

        when(repository.findById(userId)).thenReturn(Optional.empty()); //empty dönüp patlatsın istiyoruz.

        assertThrows(UserNotFoundException.class,()->userService.deactivateUser(userId));
        //when -> verify
        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository); //mock nesnesi üzerinde bir kere çağırıldığını doğrular.

    }
    @Test
    public void testActivateUser_whenIdExist_itShouldUpdateUserByActiveTrue(){  // 6+ başarılı senaryo
        String mail= "emreilgar90@gmail.com";
        Users users = new Users(userId,mail,"firstName","lastName","",false);
        Users updatedUsers = new Users(userId,mail,"firstName","lastName","",true);
        Users savedUsers = new Users(userId,mail,"firstName","lastName","",true);

        when(repository.findById(userId)).thenReturn(Optional.of(users));
        userService.activateUser(userId);

        //when -> verify
        verify(repository).findById(userId);
        verify(repository).save(savedUsers); //mock nesnesi üzerinde bir kere çağırıldığını doğrular.

    }
    @Test
    public void testActivateUser_whenIdDoesNotExist_itShouldThrowUserNotFoundException(){  // 6++ başarısız senaryo

        when(repository.findById(userId)).thenReturn(Optional.empty());//empty dönüp patlatsın istiyoruz.
        assertThrows(UserNotFoundException.class,()->
                userService.activateUser(userId));
        //when -> verify
        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository); //bir defa tetiklendi.

    }
    @Test
    public void testDeleteUser_whenIdExist_itShouldDeleteUser(){  // 7+ başarılı senaryo
        Users users = new Users(userId,"emreilgar90@gmail.com","firstName","lastName","",false);

        when(repository.findById(userId)).thenReturn(Optional.of(users));
        userService.deleteUser(userId);

        //when -> verify
        verify(repository).findById(userId);
        verify(repository).deleteById(userId); //mock nesnesi üzerinde bir kere çağırıldığını doğrular.

    }
    @Test
    public void testDeleteUser_whenIdDoesNotExist_itShouldThrowUserNotFoundException(){  // 7++ başarısız senaryo

        when(repository.findById(userId)).thenReturn(Optional.empty());//empty dönüp patlatsın istiyoruz.

        assertThrows(UserNotFoundException.class,()->
                userService.deleteUser(userId));
        //when -> verify
        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository); //bir defa tetiklendi.

    }

}