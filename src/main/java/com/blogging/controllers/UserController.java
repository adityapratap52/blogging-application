package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.UserDto;
import com.blogging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // add user in database
//    @PostMapping("/addUser")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//
//        UserDto createUserDto = this.userService.createUser(userDto);
//
//        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }


    // update user in database
    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {

        UserDto updatedUser = this.userService.updateUser(userDto);

        return ResponseEntity.ok(updatedUser);
    }


    // Delete user from database
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {

        this.userService.deleteUser(userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }


    // @PreAuthorize means -> Get list of users and only admin access
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());
    }


    // Get user by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getAllUsers(@PathVariable Integer userId) {

        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
