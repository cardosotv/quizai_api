package com.cardosotv.quizai.controllers;

import java.util.List;
import java.util.Objects;
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
import com.cardosotv.quizai.model.DTO.GameDTO;
import com.cardosotv.quizai.model.DTO.GameQuestionsDTO;
import com.cardosotv.quizai.model.services.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.websocket.server.PathParam;

/**
 * @author Tiago Cardoso on 04-03-2024
 */
@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    @Autowired
    private final GameService gameService;
    

    public GameController(GameService gameService){
        this.gameService = gameService;
    }


    @PostMapping
    @Operation(summary = "Post request for Game Domain."
         , description = "Endpoint to create the game.")
    @ApiResponse(responseCode = "200"
                , description = "Game created!")
    public ResponseEntity<GameDTO> createGame(@RequestParam UUID idSubject
                                        , @RequestHeader("token") String token){
        GameDTO result;
        try {
            result = this.gameService.createGame(idSubject, token);
        } catch (Throwable t) {
           throw HandleException.handleException(t, idSubject, "Game");
        }
        return ResponseEntity.ok(result);
    }

    
    @GetMapping
    @Operation(summary = "Get request for Games."
    , description =  "Endpoint in charge of return a list of all games.")
    @ApiResponse(responseCode =  "200", description =  "Games List found.")
    public ResponseEntity<List<GameDTO>> getAllGames( 
                                        @RequestParam(defaultValue="null") String userid
                                        , @RequestParam(defaultValue="0") int page
                                        , @RequestParam(defaultValue="5") int size
                                        , @RequestHeader("token") String token){
        
        List<GameDTO> result;
        UUID id;
        try {
            // Null treatment
            if (Objects.equals(userid, "null")){
                id = null;
            } else {
                id = UUID.fromString(userid.toString());
            }
            result = this.gameService.getAllGames((UUID )id, null, page, size);
        } catch (Throwable t) {
            throw HandleException.handleException(t, token, "Games");
        }
        return ResponseEntity.ok(result);
    }



    @GetMapping("/top10")
    @Operation(summary = "Get request for Top 10 Games."
    , description =  "Endpoint in charge of return a list of top 10 games all times.")
    @ApiResponse(responseCode =  "200", description =  "Top 10 Games List found.")
    public ResponseEntity<List<GameDTO>> getTopTenGamesEver( 
                                             @RequestHeader("token") String token){
        
        List<GameDTO> result;
        try {
            result = this.gameService.getAllGames(null, true, 0, 10);
        } catch (Throwable t) {
            throw HandleException.handleException(t, token, "GamesTop10List");
        }
        return ResponseEntity.ok(result);
    }



    @GetMapping("/{gameId}")
    @Operation(summary = "Get request for Games by ID."
    , description =  "Endpoint in charge of return the game by ID")
    @ApiResponse(responseCode =  "200", description =  "Game found.")
    public ResponseEntity<GameDTO> getAllGames( @PathVariable UUID gameId 
                                    , @RequestHeader("token") String token){
        GameDTO result;
        try {
            result = this.gameService.getGameByID(gameId);
        } catch (Throwable t) {
            throw HandleException.handleException(t, token, "Games");
        }
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{gameId}")
    @Operation(summary = "Delete request for Games by ID."
    , description =  "Endpoint in charge of delete the game by ID")
    @ApiResponse(responseCode =  "200", description =  "Game deleted.")
    public ResponseEntity<String> deleteGameByID( @PathVariable UUID gameId 
                                    , @RequestHeader("token") String token){

        try {
            this.gameService.deleteGameByID(gameId);
        } catch (Throwable t) {
            throw HandleException.handleException(t, token, "Games");
        }
        return ResponseEntity.ok("Delete request executed with success.");
    }


    @PutMapping("/question")
    @Operation(summary = "Put request for Games Question."
    , description =  "Endpoint in charge of update the game questions with the user answer.")
    @ApiResponse(responseCode =  "200", description =  "Game Question Updated.")
    public ResponseEntity<GameQuestionsDTO> updateGameQuestion( @RequestBody GameQuestionsDTO gameQuestion
                                                        , @RequestHeader("token") String token){
        GameQuestionsDTO result;
        try {
            result = this.gameService.updateGameQuestions(gameQuestion, token);
        } catch (Throwable t) {
            throw HandleException.handleException(t, gameQuestion, "GameQuestion");
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{gameID}")
    @Operation(summary = "Put request to finish the game."
    , description =  "Endpoint in charge of end the game when the user finish of answer the questions.")
    @ApiResponse(responseCode =  "200", description =  "Game Finished with Success.")
    public ResponseEntity<GameDTO> finishGame( @PathVariable UUID gameID
                                                        , @RequestHeader("token") String token){
        GameDTO result;
        try {
            result = this.gameService.finishGame(gameID, token);
        } catch (Throwable t) {
            throw HandleException.handleException(t, gameID, "GameFinish");
        }
        return ResponseEntity.ok(result);
    }
}
