package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    public Users findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.doubtCount = 0")
    void resetAllCounts();
}
