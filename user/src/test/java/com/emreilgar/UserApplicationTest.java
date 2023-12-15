package com.emreilgar;
import com.emreilgar.dto.UserDtoConverter;
import com.emreilgar.repository.UsersRepository;
import com.emreilgar.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
class UserApplicationTest {
    private UserDtoConverter converter;
    private UsersRepository repository;
    private UserService userService;

    @BeforeEach //Her "testCase" de çalışır.
    public void setUp(){
        repository= Mockito.mock(UsersRepository.class);
        converter= Mockito.mock(UserDtoConverter.class);

        userService=new UserService(repository,converter);
    }

    @Test
    public void testGetAllUsers_itShouldReturnUserDtoList(){

    }

}