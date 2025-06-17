package com.project.doubt_solver.service;

public interface AIService {
    String getAnswer(String question);
    String buildPrompt(String question);
    String extractTextFromResponse(String response);
    boolean checkComment(String question,String comment);
}
