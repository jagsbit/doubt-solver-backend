package com.project.doubt_solver.controller;

import com.project.doubt_solver.payloads.UserDto;
import com.project.doubt_solver.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUser=userService.setUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId){
        UserDto userDto=userService.getUser(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsers=userService.getAllUser();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @GetMapping("/leaders")
    public ResponseEntity<List<UserDto>> getLeaders(){
        List<UserDto> leaders=userService.getLeaders();
        return new ResponseEntity<>(leaders,HttpStatus.OK);
    }
}
