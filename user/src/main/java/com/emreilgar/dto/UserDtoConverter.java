package org.example.dto;

import org.example.model.User;
import org.example.model.UserInformation;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

//    public UserDto convert(User from){
//        return new UserDto(from.getMail(),from.getFirstName(), from.getLastName(), from.getMiddleName());
//    }

    public UserDto convert(UserInformation from){
        return new UserDto(from.getMail(),from.getFirstName(),from.getLastName(),from.getMiddleName());
    }
}
