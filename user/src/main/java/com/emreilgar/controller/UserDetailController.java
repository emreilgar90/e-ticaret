package com.emreilgar.controller;

import com.emreilgar.dto.CreateUserDetailsRequest;
import com.emreilgar.dto.UpdateUserDetailsRequest;
import com.emreilgar.dto.UserDetailsDto;
import com.emreilgar.service.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userdetails")
public class UserDetailController {
    private final UserDetailsService userDetailsService;
    public UserDetailController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/cretauserdetails")
    public ResponseEntity<UserDetailsDto> createUserDetails(@RequestBody @Valid CreateUserDetailsRequest request){
        return ResponseEntity.ok(userDetailsService.createUserDetails(request));

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto>  updateUserDetails(@PathVariable Long id, @RequestBody @Valid UpdateUserDetailsRequest request){
        return ResponseEntity.ok(userDetailsService.updateUserDetails(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsersDetails(@PathVariable Long id){
        userDetailsService.deleteUsersDetails(id);
        return ResponseEntity.ok().build();
    }





}
