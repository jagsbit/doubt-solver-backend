package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.model.EmailVerificationCode;
import com.project.doubt_solver.repository.EmailVerificationCodeRepository;
import com.project.doubt_solver.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationCodeRepository codeRepo;

    private final int CODE_EXPIRY_MINUTES = 5;

    @Override
    public String sendVerificationEmail(String to) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        EmailVerificationCode verificationCode = codeRepo.findById(to)
                .map(existing -> {
                    existing.setCode(code);
                    existing.setCreatedAt(LocalDateTime.now());
                    return existing;
                })
                .orElse(new EmailVerificationCode(to, code));

        codeRepo.save(verificationCode);

        // Send email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your Verification Code");
            message.setText("Your verification code is: " + code + ". It is valid for 5 minutes.");
            mailSender.send(message);
            return "Verification code sent to " + to;
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    @Override
    public String verifyEmail(String email, String code) {
        Optional<EmailVerificationCode> optional = codeRepo.findById(email);

        if (optional.isEmpty()) {
            return "No verification code found for this email.";
        }

        EmailVerificationCode storedCode = optional.get();

        // Check expiration
        if (Duration.between(storedCode.getCreatedAt(), LocalDateTime.now()).toMinutes() > CODE_EXPIRY_MINUTES) {
            codeRepo.deleteById(email);
            return "Verification code expired. Please request a new one.";
        }

        // Check if code matches
        if (storedCode.getCode().equals(code)) {
            codeRepo.deleteById(email); // Code is one-time use
            return "Email verified successfully!";
        } else {
            return "Invalid verification code.";
        }
    }
}
