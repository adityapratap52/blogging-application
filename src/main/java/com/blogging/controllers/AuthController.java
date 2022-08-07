package com.blogging.controllers;

import com.blogging.jwtsecurity.JwtAuthRequest;
import com.blogging.jwtsecurity.JwtAuthResponse;
import com.blogging.jwtsecurity.JwtTokenHelper;
import com.blogging.payloads.UserDto;
import com.blogging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Invalid username or password");
        }
    }

    // register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> regiserUser(@RequestBody UserDto userDto) {

        UserDto registeredUser = this.userService.registerNewUser(userDto);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
