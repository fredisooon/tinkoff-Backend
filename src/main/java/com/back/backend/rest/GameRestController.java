package com.back.backend.rest;

import com.back.backend.classes.Game;
import com.back.backend.exceptions.*;
import com.back.backend.rest.dto.GameDTO;
import com.back.backend.rest.requestsClasses.PutCardRequest;
import com.back.backend.service.GameService;
import com.back.backend.utils.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameRestController {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @GetMapping("/card")
    public ResponseEntity getRandomCard(@RequestParam long userId, @RequestParam long roomId) {
        try {
            Game game = gameService.getRandomCardForPlayer(userId, roomId);
            GameDTO gameDTO = gameMapper.mapToDTO(game, userId, roomId);

            return ResponseEntity.ok(gameDTO);
        } catch (OptionalNotFoundException | PlayerDeckNotFoundException | NoAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось загрузить игру");
        }
    }

    @GetMapping()
    public ResponseEntity getGameDTO(@RequestParam(value = "roomId") Long roomId,
                              @RequestParam(value = "userId") Long userId) {
        try {
            Game game = gameService.getPlayerGame(roomId);
            GameDTO gameDTO = gameMapper.mapToDTO(game, userId, roomId);

            return ResponseEntity.ok(gameDTO);
        } catch (OptionalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось загрузить игру");
        }
    }

    @PutMapping("/card")
    public ResponseEntity putCard(@RequestBody PutCardRequest requestData) {
        try {
            Game game = gameService.putPlayerCard(requestData);
            GameDTO gameDTO = gameMapper.mapToDTO(game, requestData.getUserId(), requestData.getRoomId());

            return ResponseEntity.ok(gameDTO);
        } catch (OptionalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось положить карту");
        }
    }
}
