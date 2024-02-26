package com.cardosotv.quizai.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class quizai {

    @GetMapping("/")
    public String hello(){
        return "Welcome to a QuizAI app...";
    }

}
