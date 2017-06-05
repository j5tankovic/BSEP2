package com.groot.education.service;


import com.groot.education.dto.LoginDTO;
import com.groot.education.model.User;

public interface AuthService {

    User login(LoginDTO credentials);
}
