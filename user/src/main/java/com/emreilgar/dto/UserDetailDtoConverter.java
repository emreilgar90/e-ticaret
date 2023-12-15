package com.emreilgar.dto;

import com.emreilgar.model.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailDtoConverter {
    public UserDetailsDto convert(UserDetails from){
        return new UserDetailsDto(
                from.getPhoneNumber(),
                from.getAddress(),
                from.getCity(),
                from.getCountry(),
                from.getPostCode());

    }

    public List<UserDetailsDto> convert(List<UserDetails> from){

        return from.stream().map(this::convert).collect(Collectors.toList());

    }


}
