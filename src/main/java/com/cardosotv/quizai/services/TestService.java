package com.cardosotv.quizai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.DTO.QuestionDTO;
import com.cardosotv.quizai.model.entities.Question;
import com.cardosotv.quizai.repositories.QuestionRepository;

import io.jsonwebtoken.lang.Objects;

public class TestService {

    @Autowired
    private QuestionRepository questionRepository;

    public TestService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    public TestService() {}

    public static void main(String[] args) throws Exception{

        TestService testService = new TestService();

        List<Question> result = testService.questionRepository.findAllActivatedQuestions();

        if (Objects.isEmpty(result)){
            throw new NotFoundException("Questions", "findQuestionsNotAnsweredByUser");
        }

        System.out.println(result.size());
    }

}
