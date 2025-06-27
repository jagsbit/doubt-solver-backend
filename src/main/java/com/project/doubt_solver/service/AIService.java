package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.QuestionDto;
import com.project.doubt_solver.payloads.QuizDto;

import java.util.List;

public interface AIService {
    String getAnswer(QuestionDto questionDto);
    String buildPrompt(String question);
    String extractTextFromResponse(String response);
    boolean checkComment(String question,String comment);
    boolean checkQuestion(String question);
    List<QuizDto> generateQuiz(String topic);
}
