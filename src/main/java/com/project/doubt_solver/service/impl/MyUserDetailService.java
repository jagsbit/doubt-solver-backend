package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.UserPrincipal;
import com.project.doubt_solver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepo.findByEmail(username);
        if(user==null) throw new UsernameNotFoundException("User not Found");
        return new UserPrincipal(user);
    }
}
