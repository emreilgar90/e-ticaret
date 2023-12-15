package com.emreilgar;
import com.emreilgar.dto.UserDto;
import com.emreilgar.model.Users;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {
    public static Long userId=100L;
    public static List<Users> generateUsers() {
        return IntStream.range(0, 5).mapToObj(i -> {
            Users users = new Users(
                    (long) i,
                    i + "@emreilgar",
                    "firstname" + i,
                    "lastName" + i,
                    "",
                    new Random().nextBoolean()
            );
            return users;
        }).collect(Collectors.toList());
    }

    public static List<UserDto> generateUserDtoList(List<Users> usersList) {
      return usersList.stream().map(from-> new UserDto(from.getMail(), from.getFirstName(), from.getLastName(), from.getMiddleName()))
              .collect(Collectors.toList());
    }
    public static Users generateUser(String mail){
        return new Users((long) userId,
                userId+mail,
                "firstname"+userId,
                "lastName"+userId,
                "",
                true);
    }
    public static UserDto generateUserDto(String mail){
        return new UserDto(
                mail,
                "firstName"+userId,
                "lastName"+userId,
                "");
    }
}