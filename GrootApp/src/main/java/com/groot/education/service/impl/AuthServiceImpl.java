package com.groot.education.service.impl;

import com.groot.education.controller.exception.AuthenticationException;
import com.groot.education.controller.exception.NotFoundException;
import com.groot.education.dto.LoginDTO;
import com.groot.education.model.User;
import com.groot.education.repository.UserRepository;
import com.groot.education.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginDTO credentials) {
        final User user = userRepository.findByUsername(credentials.getUsername()).orElseThrow(NotFoundException::new);
        if (!user.getPassword().equals(credentials.getPassword())) {
            throw new AuthenticationException();
        }
        return user;
    }

}
