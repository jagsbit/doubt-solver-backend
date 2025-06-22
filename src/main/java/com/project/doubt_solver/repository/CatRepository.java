package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CatRepository extends JpaRepository<Category,Integer> {

    // Spring will implement this for you
    Category findByCatName(String catName);

    // Or a custom ordering if you want "Others" last
    @Query(
            "SELECT c FROM Category c ORDER BY CASE WHEN c.catName = 'Others' THEN 1 ELSE 0 END, c.catName"
    )
    List<Category> findAllCategoriesWithOthersLast();
}
