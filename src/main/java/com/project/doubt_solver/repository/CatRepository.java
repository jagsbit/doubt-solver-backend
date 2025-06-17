package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Category,Integer> {
}
