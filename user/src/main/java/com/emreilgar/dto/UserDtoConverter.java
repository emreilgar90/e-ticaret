package com.emreilgar.dto;
import com.emreilgar.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {
    private final UserDetailDtoConverter converter;

    public UserDtoConverter(UserDetailDtoConverter converter) {
        this.converter = converter;
    }

    public UserDto convert(Users from) {
        return new UserDto(
                from.getMail(),
                from.getFirstName(),
                from.getLastName(),
                from.getMiddleName()
        );
    }

    public List<UserDto> convert(List<Users> fromList) {

        return fromList.stream().map(this::convert).collect(Collectors.toList());
    }
}
