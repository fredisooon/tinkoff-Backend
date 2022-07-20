package com.back.backend.rest;

import com.back.backend.exceptions.*;
import com.back.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameRestController {
    @Autowired
    private GameService gameService;

    @GetMapping("/card")
    public ResponseEntity getRandomCard(@RequestParam long userId, @RequestParam long roomId) {
        try {
            return ResponseEntity.ok(gameService.getRandomCardForPlayer(userId, roomId));
        } catch (OptionalNotFoundException | PlayerDeckNotFoundException | NoAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось загрузить игру");
        }
    }
}
