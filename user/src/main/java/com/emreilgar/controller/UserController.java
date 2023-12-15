package com.emreilgar.controller;


import com.emreilgar.dto.*;
import com.emreilgar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{mail}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String mail){
        return ResponseEntity.ok(userService.getUserByMail(mail));
    }
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequestDto userRequest){
        return ResponseEntity.ok(userService.createUser(userRequest));
    }
    @PutMapping("/{mail}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String mail,@RequestBody UpdateUserRequestDto updateUserRequest){
        return ResponseEntity.ok(userService.updateUser(mail, updateUserRequest));
    }
    @PatchMapping("/{id}") //belirli alanı güncelleme
    public ResponseEntity<Void>deactivateUser(@PathVariable Long id){
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Void>activeUser(@PathVariable Long id){
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
