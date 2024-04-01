package com.cardosotv.quizai.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.model.DTO.QuestionDTO;
import com.cardosotv.quizai.model.entities.Question;
import com.cardosotv.quizai.security.JWTUtil;
import com.cardosotv.quizai.services.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "Get Users by Subject."
         , description = "Endpoind to get the list of users by a especific subject..")
    @ApiResponse(responseCode = "200"
                , description = "Users found!")
    public ResponseEntity<List<QuestionDTO>> getQuestionsBySubject(@PathVariable UUID subjectId
    //            public ResponseEntity<List<Question>> getQuestionsBySubject(@PathVariable UUID subjectId
                                        , @RequestParam(defaultValue="0") int page
                                        , @RequestParam(defaultValue="10") int size
                                        , @RequestHeader("token") String token){
        
        List<QuestionDTO> questions;

        try {
            questions = this.questionService.listAllQuestionBySubject(subjectId, page, size);
        } catch (Throwable t) {
            throw HandleException.handleException(t, subjectId, "Question");
        }
            
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    @Operation(summary = "Post new question"
    , description = "Create new question on QuizAI database.")
    @ApiResponse(responseCode = "200"
           , description = "Question created!")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody @Valid QuestionDTO question
                                        , @RequestHeader("token") String token){
        // call the method to create the question on ServiceLayer
        QuestionDTO result;
        try {
            result = this.questionService.createQuestion(question, token);
        } catch (Throwable t) {
            // if we've any error throw treated exception
            throw HandleException.handleException(t, question, "Question");
        }
        // return response with the status request.
        return ResponseEntity.ok(result);
    }


    @PutMapping
    @Operation(summary = "Put request operation for Question."
        , description = "In charge of update question on QuizAI application.")
    @ApiResponse(responseCode = "200", description = "Question updated with success.")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody @Valid Question question
                                                    , @RequestHeader("token") String token){
        // Set up question instance 
        QuestionDTO resultQuestion;
        try {
            // Call the Service method in charge of update it.
            resultQuestion = this.questionService.updateQuestion(question, token);

        } catch (Throwable t) {
            // If error return the Treated error
            throw HandleException.handleException(t, question, "Question");
        }
        // If success return the updated question
        return ResponseEntity.ok(resultQuestion);
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "Delete request operation for Questions."
            , description =  "In charge of delete question by ID on QuizAI Application.")
    @ApiResponse(responseCode = "200", description = "Question deleted with success.")
    public ResponseEntity<String> deleteQuestion(@PathVariable UUID questionId
                                                , @RequestHeader("token") String token){
        try {
            // Call the service method to delete the requested question
            this.questionService.deleteQuestion(questionId, token);
        } catch (Throwable t) {
            // If error return the treated exception
            throw HandleException.handleException(t, questionId, "Question");
        }
        // Return the operation result
        return ResponseEntity.ok("Successfully deleted the question: " + questionId);
    }


    @GetMapping("/{questionId}")
    @Operation(summary = "Get request operation for Questions."
            , description =  "In charge of get question by ID on QuizAI Application.")
    @ApiResponse(responseCode = "200", description = "Question found with success.")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable UUID questionId
                                                , @RequestHeader("token") String token){
        // Set up question instance
        QuestionDTO question;                                                    
        try {
            // Call the service method to get the requested question
            question = this.questionService.getQuestionById(questionId);
        } catch (Throwable t) {
            // If error return the treated exception
            throw HandleException.handleException(t, questionId, "Question");
        }
        // Return the operation result
        return ResponseEntity.ok(question);
    }


    @GetMapping("/test")
    @Operation(summary = "Get request for test operations."
    , description =  "In charge of test some get lists questions.")
    @ApiResponse(responseCode = "200", description = "Question found with success.")
    public ResponseEntity<List<QuestionDTO>> getQuestionsTest(@RequestHeader("token") String token){

        UUID subjectID = UUID.fromString("0b9e5598-9810-4a48-af23-da286d60e1f1"); // Celebrites
        //UUID subjectID = UUID.fromString("75be5dcc-82ab-4a52-be37-d045bd288a92"); // News
        UUID userID = UUID.fromString(JWTUtil.getUserIdFromToken(token));
        
        List<QuestionDTO> questions = this.questionService.getQuestionsForNewGame(subjectID, userID, token);
        
        return ResponseEntity.ok(questions);
    }


}
