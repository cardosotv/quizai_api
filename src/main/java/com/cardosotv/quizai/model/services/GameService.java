package com.cardosotv.quizai.model.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.DTO.GameDTO;
import com.cardosotv.quizai.model.DTO.GameQuestionsDTO;
import com.cardosotv.quizai.model.DTO.QuestionDTO;
import com.cardosotv.quizai.model.DTO.UserDTO;
import com.cardosotv.quizai.model.entities.Game;
import com.cardosotv.quizai.model.entities.GameQuestions;
import com.cardosotv.quizai.model.entities.Question;
import com.cardosotv.quizai.model.entities.Subject;
import com.cardosotv.quizai.model.entities.User;
import com.cardosotv.quizai.model.repositories.GameQuestionsRepository;
import com.cardosotv.quizai.model.repositories.GameRepository;
import com.cardosotv.quizai.security.JWTUtil;
import com.cardosotv.quizai.config.QuizAIConfig;

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
            Subject subjectDB = this.subjectService.getSubjectByID(subjectID);    
            if(Objects.isNull(subjectDB)){
                throw new NotFoundException("Game", subjectDB);
            } 
            // Check if exists questions for the selected subject.
            List<Question> questions = this.questionService.listAllQuestionBySubEntity(subjectID, 1, 10);
            // If not, ask for the chatGPT generate it.
            // *** necessary implementation
            // Create the game with the questions that this user has not yet 
            UUID userLoggedID  = UUID.fromString(JWTUtil.getUserIdFromToken(token));
            User user = this.userService.getUserByID(userLoggedID);
            if(Objects.isNull(user)){
                throw new NotFoundException("User", userLoggedID);
            }
            // Fill the GameQuestion Object
            List<GameQuestions> gameQuestions = new ArrayList<>();
            for (Question question : questions) {
                gameQuestions.add(new GameQuestions(
                    new Date()
                    , userLoggedID
                    , question
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
                , q.getTime(), q.getScore(), q.isIsCorrect())).collect(Collectors.toList());

            gameDTO.setGameQuestions(gameQuestionsDTO);
            // gameDTO.setQuestions(new List<);

        } catch (Throwable t) {
            throw HandleException.handleException(t, subjectID, "Game");
        }
        // answered correctly.
        // If any error return the treated exception.
        return gameDTO;
    }
}
