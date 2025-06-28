package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<Users,Integer> {
    public Users findByEmail(String email);


}
