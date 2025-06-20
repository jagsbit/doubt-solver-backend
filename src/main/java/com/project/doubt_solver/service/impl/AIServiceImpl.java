package com.project.doubt_solver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.doubt_solver.payloads.GeminiResponse;
import com.project.doubt_solver.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AIServiceImpl implements AIService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private ObjectMapper objectMapper;
    private final WebClient webClient;

    public AIServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient=webClientBuilder.build();
    }
    @Override
    public String getAnswer(String question) {

        // Build the prompt

        String prompt=buildPrompt(question);
        // Query the AI Model API

        Map<String,Object> requestBody=Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                }
        );

        String response=webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return extractTextFromResponse(response);
    }

    @Override
    public String buildPrompt(String question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an expert doubt solver for students and professionals focused strictly on school, college, and academic-related subjects.\n");
        prompt.append("When a user asks a question, analyze it carefully to understand the context ");
        prompt.append("and provide a clear, accurate, and step-by-step explanation that directly addresses their doubt only if it is relevant to education.\n\n");

        prompt.append("Guidelines:\n");
        prompt.append("- Only respond to doubts related to academics such as school subjects, college topics, exams, programming, engineering, science, math, etc.\n");
        prompt.append("- If the question is unrelated to education (e.g., lifestyle, movies, slangs, personal opinions, gossip, etc.), politely respond that you're only here to help with academic-related questions and cannot answer such queries.\n");
        prompt.append("- Give a concise, relevant answer with step-by-step reasoning if applicable.\n");
        prompt.append("- Use simple, easy-to-understand language, avoiding jargon unless necessary (and define any technical terms you use).\n");
       // prompt.append("- If the question is ambiguous, ask clarifying questions before answering.\n");
        prompt.append("Answer whatever you understand don't ask follow up question");
        prompt.append("- When relevant, include examples, formulas, or references to help the user understand.\n");
        prompt.append("- If needed, mention possible errors or misconceptions and explain how to avoid them.\n\n");

        prompt.append("Examples:\n");
        prompt.append("- User: \"How do I find the derivative of x²?\"\n");
        prompt.append("- Gemini: \"The derivative of x² with respect to x is found using the power rule, which says that d/dx of xⁿ = n*xⁿ⁻¹. So, for x², the derivative is 2x.\"\n\n");
        prompt.append("- User: \"Who is the best actor in Bollywood?\"\n");
        prompt.append("- Gemini: \"I'm here to help only with academic-related questions. Please ask me something related to school, college, or education.\"\n\n");

        prompt.append("Now, answer the user’s question below:\n\n");
        prompt.append("User’s Question: ").append(question);
        return prompt.toString();
    }


    @Override
    public String extractTextFromResponse(String response) {
        try {
            GeminiResponse geminiResponse=objectMapper.readValue(response, GeminiResponse.class);
            if(geminiResponse.getCandidates()!=null && !geminiResponse.getCandidates().isEmpty()){
                GeminiResponse.Candidate firstCandidate=geminiResponse.getCandidates().get(0);
                if(firstCandidate.getContent()!=null
                        && firstCandidate.getContent().getParts()!=null
                        && !firstCandidate.getContent().getParts().isEmpty()){
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }

            return "No content Found";
        }

        catch (Exception e){
            return "Error Parsing"+e.getMessage();
        }
    }

    @Override
    public boolean checkComment(String question, String comment) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a helpful assistant that checks whether a comment is relevant to a question.\n\n");
        prompt.append("Given a question and a comment on that question, determine whether the comment is related to the topic of the question, provides context, clarification, or follows the discussion.\n\n");
        prompt.append("Do not check whether the comment is correct or incorrect — only check whether it is relevant to the question’s topic or helpful in continuing the discussion.\n\n");
        prompt.append("If the comment is relevant to the question, respond with **true**.\n");
        prompt.append("If the comment is off-topic, spam, a greeting, or otherwise unrelated to the question, respond with **false**.\n\n");
        prompt.append("Respond only with \"true\" or \"false\" (without quotes) — no extra explanation.\n\n");
        prompt.append("Here is the question:\n");
        prompt.append(question).append("\n\n");
        prompt.append("Here is the comment:\n");
        prompt.append(comment);


        Map<String,Object> requestBody=Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                }
        );

        String response=webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        String answer=extractTextFromResponse(response);
        return answer.trim().equals("true");

    }
}
