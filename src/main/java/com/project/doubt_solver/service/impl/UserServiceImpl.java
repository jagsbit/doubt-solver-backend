package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.exceptions.ResourceNotFoundException;
import com.project.doubt_solver.model.Comment;
import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.Pair;
import com.project.doubt_solver.payloads.UserDto;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public UserDto setUser(UserDto userDto) {
        // mapping UserDto-Users
        Users user=modelMapper.map(userDto,Users.class);
        // save in database
        Users savedUser=userRepo.save(user);

        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto getUser(int userId) {

        Users user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
       List<Users> allUsers=userRepo.findAll();
       List<UserDto> users=allUsers.stream().map((user)->modelMapper.map(user,UserDto.class)).toList();
       return users;
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        return null;
    }

    @Override
    public String deleteUser(int userId) {
        return "";
    }

    @Override
    public List<UserDto> getLeaders() {
        List<Users> allUsers=userRepo.findAll();
        int n=allUsers.size();
        List<Pair> users=new ArrayList<>();
        for(int i=0;i<n;i++){
            int count=0;
            for(Comment comment:allUsers.get(i).getComments()){
                 count+=comment.getUpvotes().size();
            }
            Users user=allUsers.get(i);
            users.add(new Pair(user,count));
        }
        users.sort((p, q) -> q.count - p.count);
        List<UserDto> leaderDtolist=new ArrayList<>();
        int i=0;
        while(i<3 && i<n ){
            leaderDtolist.add(modelMapper.map(users.get(i).user,UserDto.class));
            i++;
        }
        return leaderDtolist;
    }
}
