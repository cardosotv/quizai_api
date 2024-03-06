package com.cardosotv.quizai.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.model.DTO.GameDTO;
import com.cardosotv.quizai.model.entities.Game;
import com.cardosotv.quizai.model.services.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
            result = this.gameService.getAllGames((UUID )id, page, size);
        } catch (Throwable t) {
            throw HandleException.handleException(t, token, "Games");
        }
        return ResponseEntity.ok(result);
    }


}
