package com.project.doubt_solver.controller;

import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.LoginDto;
import com.project.doubt_solver.payloads.LoginResponse;
import com.project.doubt_solver.payloads.UserDto;
import com.project.doubt_solver.service.AuthService;
import com.project.doubt_solver.service.EmailVerificationService;
import com.project.doubt_solver.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private EmailVerificationService emailService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        UserDto savedUser=authService.register(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto){
        LoginResponse response=authService.login(loginDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        return ResponseEntity.ok(emailService.sendVerificationEmail(email));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(
            @RequestParam String email,
            @RequestParam String code
    ) {
        return ResponseEntity.ok(emailService.verifyEmail(email, code));
    }
}
