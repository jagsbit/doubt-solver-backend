package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Category;
import com.project.doubt_solver.model.Doubt;
import com.project.doubt_solver.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoubtRepository extends JpaRepository<Doubt,Integer> {

    Page<Doubt> findByCategory(Category category, Pageable pageable);
    Page<Doubt> findByUser(Users user, Pageable pageable);


}
