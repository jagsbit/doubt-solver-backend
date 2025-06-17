package com.project.doubt_solver.service;

public interface EmailVerificationService {

     String sendVerificationEmail(String to);
     String verifyEmail(String email, String code);
}
