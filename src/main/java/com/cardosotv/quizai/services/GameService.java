package com.cardosotv.quizai.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.DTO.GameDTO;
import com.cardosotv.quizai.model.DTO.GameQuestionsDTO;
import com.cardosotv.quizai.model.DTO.OptionDTO;
import com.cardosotv.quizai.model.DTO.QuestionDTO;
import com.cardosotv.quizai.model.entities.Game;
import com.cardosotv.quizai.model.entities.GameQuestions;
import com.cardosotv.quizai.model.entities.Option;
import com.cardosotv.quizai.model.entities.Question;
import com.cardosotv.quizai.model.entities.Subject;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.repositories.GameQuestionsRepository;
import com.cardosotv.quizai.repositories.GameRepository;
import com.cardosotv.quizai.security.JWTUtil;

/**
 * @author Tiago Cardoso on 04-03-2024
 */
@Service
public class GameService {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final GameQuestionsRepository gameQuestionsRepository;
    @Autowired
    private final SubjectService subjectService;
    @Autowired
    private final QuestionService questionService;
    @Autowired
    private final UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    public GameService(GameRepository gameRepository
                , GameQuestionsRepository gameQuestionsRepository
                , SubjectService subjectService
                , QuestionService questionService
                , UserService userService
                , ModelMapper modelMapper){
        this.gameRepository = gameRepository;
        this.gameQuestionsRepository = gameQuestionsRepository;
        this.subjectService = subjectService;
        this.questionService = questionService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    public GameDTO createGame(UUID subjectID, String token) {
        // Set the return object instance
        GameDTO gameDTO;
        try {
            // check if the subject exists 
            Subject subjectDB = this.subjectService.getSubjectByID(subjectID);    
            if(Objects.isNull(subjectDB)){
                throw new NotFoundException("Game", subjectDB);
            } 

            // Check if exists questions for the selected subject.
            //List<Question> questions = this.questionService.listAllQuestionBySubEntity(subjectID, 1, 10);
            UUID userLoggedID  = UUID.fromString(JWTUtil.getUserIdFromToken(token));
            List<QuestionDTO> questions = this.questionService.getQuestionsForNewGame(subjectID, userLoggedID, token);

            // Create the game with the questions that this user has not yet 
            User user = this.userService.getUserByID(userLoggedID);
            if(Objects.isNull(user)){
                throw new NotFoundException("User", userLoggedID);
            }

            // Fill the GameQuestion Object
            List<GameQuestions> gameQuestions = new ArrayList<>();
            for (QuestionDTO question : questions) {
                gameQuestions.add(new GameQuestions(
                    new Date()
                    , userLoggedID
                    , modelMapper.map(question, Question.class)
                    , 0, 0
                    , false)
                    );
                
                }                        
            Game game = new Game();
            game.setCreatedDate(new Date());
            game.setCreatedBy(user.getId());
            game.setStartedDate(new Date());
            game.setSubject(subjectID);
            game.setScore(0);
            game.setUser(user);
            game.setQuestions(gameQuestions);
            Game gameDB = this.gameRepository.save(game);
            //After created the Game update the Questions with gameID returned by previous step
            if (gameDB.getId() != null) {            
                // Set the reference to the question in each associated option
                gameDB.getQuestions().forEach(question -> question.setGame(gameDB));
                // Save the associeted options
                gameDB.getQuestions().forEach(this.gameQuestionsRepository::save);
            }
            // convert the result for DTO with Object Mapper
            gameDTO = this.modelMapper.map(gameDB, GameDTO.class);
            
            List<GameQuestionsDTO> gameQuestionsDTO;
            

            gameQuestionsDTO = gameDTO.getGameQuestions().stream().map(q -> new GameQuestionsDTO(q.getId()
                , this.questionService.getQuestionById(q.getQuestion().getId())
                , q.getTime(), q.getScore(), q.isIsCorrect(), null)).collect(Collectors.toList());

            gameDTO.setGameQuestions(gameQuestionsDTO);
            // gameDTO.setQuestions(new List<);

        } catch (Throwable t) {
            throw HandleException.handleException(t, subjectID, "Game");
        }
        // answered correctly.
        // If any error return the treated exception.
        return gameDTO;
    }


    // responsable for delivery to request all Games as the especificate params
    public List<GameDTO> getAllGames(UUID userID, Boolean ranking, int page, int size) {

        List<GameDTO> result = new ArrayList<>();
        Page<Game> games;
        try {
            // Get the games list
            if(!Objects.isNull(userID)){
                games = this.gameRepository.findByUserId(userID, PageRequest.of(page, size));
            } else if(!Objects.isNull(ranking)) {
                games = this.gameRepository.findTop10ByOrderByScoreDesc(PageRequest.of(page, size));
            } else {
                games = this.gameRepository.findAll(PageRequest.of(page, size));
            }
            // Check if it is empty
            if(games.getContent().size() == 0){
                // If yes return the Not Found Exception
                throw new NotFoundException("Games", null);
            }
            // If not map the games list for DTO model
            for (Game game : games.getContent()) {
                result.add(modelMapper.map(game, GameDTO.class));
            }
        } catch (Throwable t) {
            throw HandleException.handleException(t, null, "Game");
        } 
        // return the result
        return result;
    }


    // responsable for delivery to request all Games by ID 
    public GameDTO getGameByID(UUID id) {

        GameDTO result;
        try {
            // check if the Game exists by informed ID
            @SuppressWarnings("null")
            Game game = this.gameRepository.findById(id).orElse(null);
            if(Objects.isNull(game)){
                // If yes return the Not Found Exception
                throw new NotFoundException("Games", null);
            }
            // If not map the games list for DTO model
            result = modelMapper.map(game, GameDTO.class);
        } catch (Throwable t) {
            throw HandleException.handleException(t, null, "Game");
        } 
        // return the result
        return result;
    }


    // Responsable for delete the game and game question informed
    @SuppressWarnings("null")
    public void deleteGameByID(UUID gameID){

        // Check if the game informed exists
        Game game = this.gameRepository.findById(gameID).orElse(null);
        try {
            // If not return Not Found Exception
            if (Objects.isNull(game)){
                throw new NotFoundException("Delete Game", game);
            }
            // Get the question from this game
            game.getQuestions().forEach(question -> this.gameQuestionsRepository.delete(question));

            // If yes delete first the questions from this game
            this.gameRepository.delete(game);
            // After that delete the game 
            
        } catch (Throwable t) {
            // If any error return the treated exceptio
            throw HandleException.handleException(t, gameID, "Game/Delete");
        }

    }
    
    
    @SuppressWarnings("null")
    public GameQuestionsDTO updateGameQuestions(GameQuestionsDTO gameQuestion
                                , String token){
        GameQuestions gameDB;
        try {
            // Check if the question informed exists 
            gameDB = this.gameQuestionsRepository.findById(gameQuestion.getId()).orElse(null);
            // If not return the Not Found Exception
            if (Objects.isNull(gameDB)){
                throw new NotFoundException("GameQuestion", gameQuestion);
            }
            // Get the userId from token to save the audit information
            UUID userID = UUID.fromString(JWTUtil.getUserIdFromToken(token));
            
            // Check if the answer is correct
            if (Objects.isNull(gameQuestion.getAnswer())){
                throw new NotFoundException("Answer is null.", gameQuestion);
            }   
            
            Boolean isCorrect = checkIfAnswerIsCorrect(gameQuestion.getQuestion().getId()
            , gameQuestion.getAnswer().getId());   
            
            Option answer = new Option();
            answer.setIsCorrect(isCorrect);
            answer.setId(gameQuestion.getAnswer().getId());
            answer.setOption(gameQuestion.getAnswer().getOption());
            
            // Update gamequestion data 
            gameDB.setUpdatedDate(new Date());
            gameDB.setUpdatedBy(userID);
            gameDB.setTime(gameQuestion.getTime());
            gameDB.setScore(calculateScoreByAnswer(gameQuestion.getTime(), isCorrect));
            gameDB.setIsCorrect(isCorrect);
            gameDB.setAnswer(answer);
    
            // Save the updated object on database
            gameDB = this.gameQuestionsRepository.save(gameDB);
            
        } catch (Throwable t) {
            throw HandleException.handleException(t, gameQuestion, "GameQuestion/update");
        }
        // return with the DTO mapped
        return modelMapper.map(gameDB, GameQuestionsDTO.class) ;
    } 


    @SuppressWarnings("null")
    public GameDTO finishGame(UUID gameID, String token){
        // Check if the game informed exists
        Game game;
        try {
            game = this.gameRepository.findById(gameID).orElse(null);    
            // If not return the Not Found Exception
            if(Objects.isNull(game)){
                throw new NotFoundException("FinishGame", gameID);
            }
            // Get the user throught token to audit information
            UUID userAuditID = UUID.fromString(JWTUtil.getUserIdFromToken(token));
            // Sum the score from all questions
            int summed_score = 0;
            for (GameQuestions question : game.getQuestions()) {
                summed_score = summed_score + question.getScore();
            }
            // Update the game with Score and FinishDate information
            game.setUpdatedBy(userAuditID);
            game.setUpdatedDate(new Date());
            game.setFinishedDate(new Date());
            game.setScore(summed_score);
            game = this.gameRepository.save(game);

            // update the User Summary Score
            game.setUser(this.userService.updateUserScore(game.getUser().getId(), summed_score, token));

        } catch (Throwable t) {
            // If any erro return treated exception
            throw HandleException.handleException(t, gameID, "FinishGame");
        }
        // Return the GameDTO object as result
        return modelMapper.map(game, GameDTO.class);
    }


    private Boolean checkIfAnswerIsCorrect(UUID questionID, UUID optionID) {
        // Inizializated the result 
        Boolean result = false;
        try {
            // Get the question from the option informed
            QuestionDTO question = this.questionService.getQuestionById(questionID);
            // Check if the option informed is correct
            for (OptionDTO optionDB : question.getOptions()) {
                if (Objects.equals(optionDB.getId(), optionID)){
                    result = optionDB.isIsCorrect();
                }
            }
            // If any error return treated exception
        } catch (Throwable t) {
            throw HandleException.handleException(t, optionID, "GameQuestion");
        }
        // Return the result True or False
        return result;
    }


    /**Responsable for calculate the score for each question
    // If the answer is correct win 50 pts
    // The rest of the points are calculate in accord of the time
    // for each 1 seg the score decrease 5 pts startinf with 50 pts
    // The max score is 100 pts
    **/
    private int calculateScoreByAnswer(int time, Boolean isCorrect){

        int result = 0;
        try{
            if (isCorrect) {
                result = (result + 50) + (time/10)*5;
            }
        } catch(Throwable t) {
            throw HandleException.handleException(t, time, "calculateScoreByAnswer");
        }
        return result;
    }


    
    
}
