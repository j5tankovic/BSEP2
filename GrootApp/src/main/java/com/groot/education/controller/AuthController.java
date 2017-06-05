package com.groot.education.controller;

import com.groot.education.dto.LoggedUserDTO;
import com.groot.education.dto.LoginDTO;
import com.groot.education.model.User;
import com.groot.education.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO credentials) {
        final User user = authService.login(credentials);
        final LoggedUserDTO loggedUser = convertToDTO(user);
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }


    private LoggedUserDTO convertToDTO(User user) {
        LoggedUserDTO loggedUser = new LoggedUserDTO();
        loggedUser.setId(user.getId());
        loggedUser.setToken(user.getToken());
        loggedUser.setRole(user.getRole());
        loggedUser.setPermissions(user.getPermissions());
        return loggedUser;
    }
}
