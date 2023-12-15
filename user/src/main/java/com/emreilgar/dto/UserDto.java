package com.emreilgar.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String mail;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<UserDetailsDto> userDetails;


    public UserDto(String mail, String firstName, String lastName, String middleName) {
    }
}
