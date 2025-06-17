package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Comment;

import com.project.doubt_solver.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByUser(Users user);
}
