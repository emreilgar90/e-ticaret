package com.emreilgar.service;

import com.emreilgar.dto.CreateUserDetailsRequest;
import com.emreilgar.dto.UpdateUserDetailsRequest;
import com.emreilgar.dto.UserDetailDtoConverter;
import com.emreilgar.dto.UserDetailsDto;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.exception.UserDetailsNotFoundException;
import com.emreilgar.model.Users;
import com.emreilgar.model.UserDetails;
import com.emreilgar.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService; // UserDetails <-> UserService arasında ki bağlantı için
    private final UserDetailDtoConverter converter;
    public UserDetailsService(UserDetailsRepository userDetailsRepository, UserService userService, UserDetailDtoConverter converter) {
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
        this.converter = converter;
    }
    public UserDetailsDto createUserDetails(final CreateUserDetailsRequest request){
        Users users = userService.findUserById(request.getUserId());

        UserDetails userDetails = new UserDetails(
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getCountry(),
                request.getPostCode(),
                users);
        return converter.convert(userDetailsRepository.save(userDetails));
    }

    public UserDetailsDto updateUserDetails(final Long userDetailsId,final UpdateUserDetailsRequest request){
        UserDetails userDetails = userDetailsRepository.findUserDetailsById(userDetailsId);

        UserDetails updateUserDetails = new UserDetails(
                userDetails.getId(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getCountry(),
                request.getPostCode(),
                userDetails.getUsers());
        return converter.convert(userDetailsRepository.save(updateUserDetails));
    }

    public void deleteUsersDetails(final Long id){
        findUserDetailsById(id);
        userDetailsRepository.deleteById(id);

    }

    private UserDetails findUserDetailsById(final Long userDetailsId){
        return userDetailsRepository.findById(userDetailsId)
                .orElseThrow(()-> new UserDetailsNotFoundException(ErrorType.USER_DETAILS_NOT_FOUND));

    }

}
