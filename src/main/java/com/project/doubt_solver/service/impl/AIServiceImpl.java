package com.project.doubt_solver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.GeminiResponse;
import com.project.doubt_solver.payloads.QuestionDto;
import com.project.doubt_solver.payloads.QuizDto;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
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

    @Autowired
    private UserRepository userRepository;

    public AIServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient=webClientBuilder.build();
    }
    @Override
    public String getAnswer(QuestionDto questionDto) {

        String email=questionDto.getEmail();
        String question=questionDto.getQuestion();

        Users user=userRepository.findByEmail(email);

        if(user.getDoubtCount()>=100 && user.getRole().equals("normal")) return "You’ve exceeded your free usage limit for today. Please upgrade to a standard subscription to continue.";

        // check question and return
        if(!checkQuestion(question)) return "Your doubt is not related to Academics";
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
         user.setDoubtCount(user.getDoubtCount()+1);
         userRepository.save(user);
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
        prompt.append("If you don't understand the question or if fail to decode the question than don't give answer, give response like your doubt is not clear or incomplete doubt");
        prompt.append("- When relevant, include examples, formulas, or references to help the user understand.\n");
        prompt.append("- If needed, mention possible errors or misconceptions and explain how to avoid them.\n\n");
        prompt.append("don't give the question in response");

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
    public boolean checkQuestion(String question) {
        // Build the prompt
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a helpful assistant that checks whether a question is related to school, college, or academics.\n\n");
        prompt.append("Given a question, determine whether it is about school, college, academics, studies, learning, homework, coursework, exams, teaching, education, etc.\n\n");
        prompt.append("If the question is related to school, college, or academics, respond with **true**.\n");
        prompt.append("If the question is not related to these, respond with **false**.\n\n");
        prompt.append("Respond only with \"true\" or \"false\" (without quotes) — no extra explanation.\n\n");
        prompt.append("Here is the question:\n");
        prompt.append(question);

        // Prepare request payload
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt.toString())
                        })
                }
        );

        // Send the request
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Parse the answer
        String answer = extractTextFromResponse(response);
        return answer.trim().equalsIgnoreCase("true");
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

    @Override
    public List<QuizDto> generateQuiz(String topic) {
        // 1. Build the prompt
        String prompt = buildQuizPrompt(topic);

        // 2. Prepare request body
        Map<String,Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        // 3. Call Gemini
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 4. Extract plain text
        String plainText = extractTextFromResponse(response);

        // 5. Parse the questions
        return parseQuestionsFromPlainText(plainText);
    }

    private String buildQuizPrompt(String topic) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a quiz generator. Generate 10 unique multiple-choice questions on the topic: ")
                .append(topic).append(".\n\n");
        prompt.append("Each question must have 4 options labeled A, B, C, D and one correct answer label. ");
        prompt.append("Provide your response as a JSON array where each element contains: ");
        prompt.append("{\"question\":...,\"optionA\":...,\"optionB\":...,\"optionC\":...,\"optionD\":...,\"answer\":...}. ");
        prompt.append("Do not add any extra text outside this JSON array.");
        return prompt.toString();
    }

    private List<QuizDto> parseQuestionsFromPlainText(String plainText) {
        try {
            // Trim the input
            plainText = plainText.trim();

            // Strip Markdown code block markers if present
            if (plainText.startsWith("```")) {
                plainText = plainText.replaceAll("^```(json)?\\s*", "").replaceAll("\\s*```$", "");
            }

            return objectMapper.readValue(
                    plainText,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, QuizDto.class)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }




}
