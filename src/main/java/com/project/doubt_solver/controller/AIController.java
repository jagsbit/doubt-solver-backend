package com.project.doubt_solver.controller;

import com.project.doubt_solver.payloads.QuestionDto;
import com.project.doubt_solver.payloads.QuizDto;
import com.project.doubt_solver.service.impl.AIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIServiceImpl aiService;



    @PostMapping("")
    public ResponseEntity<String> getAnswer(@RequestBody QuestionDto questionDto){

        String answer=aiService.getAnswer(questionDto);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PostMapping("/generate-quiz")
    public ResponseEntity<List<QuizDto>> generateQuiz(@RequestBody String topic){
        List<QuizDto> quizDtoList=aiService.generateQuiz(topic);
        return new ResponseEntity<>(quizDtoList,HttpStatus.OK);
    }
}
