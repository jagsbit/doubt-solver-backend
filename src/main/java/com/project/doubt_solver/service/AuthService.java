package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.LoginDto;
import com.project.doubt_solver.payloads.LoginResponse;
import com.project.doubt_solver.payloads.UserDto;

public interface AuthService {

    UserDto register(UserDto userDto);
    LoginResponse login(LoginDto loginDto);
    String sendVerificationEmail(String to);
    String verifyEmail(String message);

}
