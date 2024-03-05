package com.cardosotv.quizai.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizAIConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
