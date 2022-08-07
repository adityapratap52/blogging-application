package com.blogging.services;

import com.blogging.entities.User;
import com.blogging.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
