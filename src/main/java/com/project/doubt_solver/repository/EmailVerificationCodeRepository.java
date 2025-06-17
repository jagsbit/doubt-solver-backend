package com.project.doubt_solver.repository;

import com.project.doubt_solver.model.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, String> {
}
