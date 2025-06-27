package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.LoginDto;
import com.project.doubt_solver.payloads.LoginResponse;
import com.project.doubt_solver.payloads.UserDto;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;



    private   BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @Override
    public UserDto register(UserDto userDto) {

        Users user=modelMapper.map(userDto,Users.class);
        user.setPassword(encoder.encode(user.getPassword()));
        Users savedUser=userRepo.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {

        Users users=modelMapper.map(loginDto, Users.class);
        Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword()));
        if (auth.isAuthenticated()) {
            Users loggedInuser=userRepo.findByEmail(loginDto.getEmail());
            return  new LoginResponse(jwtService.generateToken(users.getEmail()),modelMapper.map(loggedInuser, UserDto.class));
        }
        return  new LoginResponse("Invalid credentials");

    }

    @Override
    public String sendVerificationEmail(String to) {
        return "";
    }

    @Override
    public String verifyEmail(String message) {
        return "";
    }
}
