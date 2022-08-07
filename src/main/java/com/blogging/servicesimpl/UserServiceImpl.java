package com.blogging.servicesimpl;

import com.blogging.entities.Role;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.RoleRepo;
import com.blogging.repositories.UserRepo;
import com.blogging.services.UserService;
import com.blogging.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepo.findById(502).orElseThrow(() -> new ResourceNotFoundException("Role", "RoleId", AppConstants.ROLE_NORMAL));
        user.getRoles().add(role);

        User registeredUser = this.userRepo.save(user);

        return this.modelMapper.map(registeredUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

//        User user = dtoToUser(getUserById(userId));
//
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(this.dtoToUser(userDto));

        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        return users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = dtoToUser(getUserById(userId));

        this.userRepo.delete(user);
    }


    // THis method convert dto to user type
    public User dtoToUser(UserDto userDto) {

                // We can use ModelMapper instead of
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        User user = this.modelMapper.map(userDto, User.class);

        return user;
    }

    // THis method convert user to userDto type
    public UserDto userToDto(User user) {

//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;
    }
}
