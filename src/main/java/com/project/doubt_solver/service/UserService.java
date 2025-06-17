package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto setUser(UserDto userDto);
    UserDto getUser(int userId);
    List<UserDto> getAllUser();
    UserDto updateUser(UserDto userDto,int userId);
    String deleteUser(int userId);
    List<UserDto> getLeaders();

}
