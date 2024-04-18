package com.cardosotv.quizai.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.entities.Subject;
import com.cardosotv.quizai.repositories.OptionRepository;
import com.cardosotv.quizai.repositories.QuestionRepository;
import com.cardosotv.quizai.repositories.SubjectRepository;
import com.cardosotv.quizai.model.entities.Option;
import com.cardosotv.quizai.model.entities.Question;
import com.cardosotv.quizai.model.DTO.*;

import com.cardosotv.quizai.security.JWTUtil;



@Service
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private final OptionRepository optionRepository;
    @Autowired
    private final SubjectRepository subjectRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Value("${com.cardoso.quizai.apikey}")
    private String apiKeyOpenAI;

    @Value("${com.cardosotv.quizai.enpointopenai}")
    private String endpointOpenAI;
    

    public QuestionService(QuestionRepository questionRepository 
                            , OptionRepository optionRepository
                            , SubjectRepository subjectRepository
                            , ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    public List<QuestionDTO> listAllQuestionBySubject(UUID subjectId, int page, int size){

        List<Question> questions;
        try {
            // Changed for return just active questions (logical delete)
            // questions = this.questionRepository.findAll();
            questions = this.questionRepository.findAllActivatedQuestions(); 
            if (questions.size() == 0){
                throw new NotFoundException("Question", subjectId);
            }
        } catch (Throwable t) {
            throw HandleException.handleException(t, subjectId, "Question");
        }
        return mapToQuestionsDto(questions);
    }

    public List<Question> listAllQuestionBySubEntity(UUID subjectId, int page, int size){

        List<Question> questions;
        try {
            // Changed for return just active questions (logical delete)
            // questions = this.questionRepository.findAll();
            questions = this.questionRepository.findAllActivatedQuestions(); 
            if (questions.size() == 0){
                throw new NotFoundException("Question", subjectId);
            }
        } catch (Throwable t) {
            throw HandleException.handleException(t, subjectId, "Question");
        }
        return questions;
    }

    // In charge of get the question by Id informed on client request
    @SuppressWarnings("null")
    public QuestionDTO getQuestionById(UUID questionId){
        // Set up question instance\
        Question question;
        try {
            // Get the question if exists
            question = this.questionRepository.findById(questionId).orElse(null);            
            // If not exists return the Not Found Exception
            if(Objects.isNull(question)){
                throw new NotFoundException("Question", questionId);
            }
        } catch (Throwable t) {
            // Return treated exception if any error
            throw HandleException.handleException(t, questionId, "Question");
        }
        // Return the question found
        return mapQuestionToDto(question);
    }

    @SuppressWarnings("null")
    public QuestionDTO createQuestion(QuestionDTO question, String token) {
        // check if the subjectID informed exists
        Question result;
        try {
            Subject subject = this.subjectRepository.findById(question.getSubjectID()).orElse(null);
            // If not exists throw the exception NotFound
            if (Objects.isNull(subject)) {
                throw new NotFoundException("Question", question);
            }
            // If exists create first the question on database
            // Get the atribute createBy from token
            UUID createdBy = UUID.fromString(JWTUtil.getUserIdFromToken(token));
            
            Question questionDB = modelMapper.map(question, Question.class);

            // Update the field createdBY and createdDate
            questionDB.setCreatedBy(createdBy);
            questionDB.setCreatedDate(new Date());
            
            // Save que question on database
            result = this.questionRepository.save(questionDB);
            
        //    After that create the options with the questionID returned by previous step
            if (result.getId() != null) {
                // Set the reference to the question in each associated option
                result.getOptions().forEach(option -> option.setQuestion(result));
                // Save the associeted options
                result.getOptions().forEach(optionRepository::save);
            }
        // If any error handle it with a CustomException 
        } catch (Throwable t) {
            throw HandleException.handleException(t, question, "Question");
        }
        // return the Question created to controller
        return mapQuestionToDto(result);
    }


    // Method in charge of update the question object on database
    @SuppressWarnings("null")
    public QuestionDTO updateQuestion(Question question, String token){
        Question questionDB;
        try {
            // Check if the question informed exists
            questionDB = this.questionRepository.findById(question.getId()).orElse(null);
            if (Objects.isNull(questionDB)){
                // if not, exists throw the Not Found Exception
                throw new NotFoundException("Question", question);
            }
            // if yes, update the question from DB with the question from request
            questionDB.updateObject(question);
            // update the audit information
            questionDB.setUpdatedBy(UUID.fromString(JWTUtil.getUserIdFromToken(token)));
            questionDB.setUpdatedDate(new Date());
            // save the updated question on database
            questionDB = this.questionRepository.save(questionDB);
            // After that update the options with the questionID returned by previous step
            // Set the reference to the question in each associated option
            questionDB.getOptions().forEach(option -> option.setQuestion(question));
            // Update the associeted options
            questionDB.getOptions().forEach(optionRepository::save);
        } catch (Throwable t) {
            // Set the custom treatment exception if any error
            throw HandleException.handleException(t, question, "Question");
        }
        // return the updated question to controller
        return mapQuestionToDto(questionDB);
    }

    // In charge of delete the question requested by questionID
    @SuppressWarnings("null")
    public void deleteQuestion(UUID questionID, String token){
        // set up question instance
        Question question;
        try {
            // Check if the question exists
            question = this.questionRepository.findById(questionID).orElse(null);
            // if not throw the Not Found Exception
            if(Objects.isNull(question)){
                throw new NotFoundException("Question", questionID);
            }
            // **** Change to do a logical delete
            // get the audit information from token
            question.setDeletedBy(UUID.fromString(JWTUtil.getUserIdFromToken(token)));
            question.setDeletedDate(new Date());
            // First, delete options from this question
            //question.getOptions().forEach(optionRepository::delete);
            // Do the question logical delete
            this.questionRepository.save(question);
            //this.questionRepository.delete(question);
        } catch (Throwable t) {
            // If any error return treated exception
            throw HandleException.handleException(t, questionID, "Question");
        }
    }

    private List<QuestionDTO> mapToQuestionsDto(List<Question> questions){
        return questions.stream().map(this::mapQuestionToDto).collect(Collectors.toList());
    }

    private QuestionDTO mapQuestionToDto(Question question){

        List<OptionDTO> optionDTOs = question.getOptions().stream().map(option -> new OptionDTO(
            option.getId(), option.getOption(), option.isIsCorrect())).collect(Collectors.toList());

        return new QuestionDTO(question.getId(), question.getQuestion(), optionDTOs, question.getIdSubject());
    }


    /**
     * Returns the question list for create a new game in according of the user.
     * @param subjectID Subject identifier.
     * @param userID User identifier.
     * @return List of questions.
     */
    @SuppressWarnings("null")
    public List<QuestionDTO> getQuestionsForNewGame(UUID subjectID, UUID userID, String token){

        // Instantiate the list result 
        List<QuestionDTO> result = new ArrayList<>();
        
        // Get the list of question by subject that the user have never answered correctly.
        List<Question> questions = this.questionRepository.findQuestionsNotAnsweredByUser(userID, subjectID);

        // Check if the list is not null
        if (Objects.isNull(questions)){
            throw new NotFoundException("Questions", "getQuestionsForNewGame");
        }
        
        // Check if this list has questions enough for the game (qtd = 10)
        if (questions.size() < 10){
        
            // If not, ask to Chatgpt for missing questions.
            int missing_questions = 10 - questions.size();

            Subject subject = this.subjectRepository.findById(subjectID).orElse(null);

            // Check if the list is not null
            if (Objects.isNull(subject)){
                throw new NotFoundException("Subject", subjectID);
            }

            // Request questions for ChatGPT
            OpenAIService openAIService = new OpenAIService();
            List<QuestionDTO> questionsChatGPT = openAIService.requestNewQuestionByUser(null
                                                                    , missing_questions
                                                                    , subject.getName()
                                                                    , apiKeyOpenAI
                                                                    , endpointOpenAI);
        
            // Append the question from ChatGPT to result list
            for (QuestionDTO question : questionsChatGPT) {
                question.setSubjectID(subjectID);
                QuestionDTO questionDTO = this.createQuestion(question, token);
                result.add(questionDTO);
            }

        }

        // Append chatgpt questions to general question list 
        for (Question question : questions) {
            result.add(modelMapper.map(question, QuestionDTO.class));
        }

        // Return the complete question list
        return result;
    }
    

    /**
     * @author Tiago Cardoso - 18/04/2024
     * Purpose: Returns the correct option for especific question
     * @param questionID Question identifier.
     * @return OptionDTO Object with correct option.
     */
    Option getCorrectOptionByQuestion(UUID questionID) {

        Option correctOption;

        try {
            //correctOption = questionRepository.findCorrectOptionByQuestion(questionID);
            correctOption = optionRepository.findCorrectOptionByQuestion(questionID);

            if (Objects.isNull(correctOption)){
                throw new NotFoundException("Correct Option", questionID);
            }  

        } catch (Throwable t) {
            // TODO: handle exception
            throw HandleException.handleException(t, questionID, "Correct Option");
        }

        return correctOption;
    }

     /**
     * @author Tiago Cardoso - 18/04/2024
     * Purpose: Returns the correct option for especific question
     * @param questionID Question identifier.
     * @return OptionDTO Object with correct option.
     */
    Option getOptionByID(UUID optionID) {

        Option option;

        try {
            option = optionRepository.findById(optionID).orElse(null);

            if (Objects.isNull(option)){
                throw new NotFoundException("Correct Option", optionID);
            }  

        } catch (Throwable t) {
            // TODO: handle exception
            throw HandleException.handleException(t, optionID, "Get option by ID.");
        }

        return option;
    }

}
