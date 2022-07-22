package com.back.backend.rest;

import com.back.backend.rest.requestsClasses.CreatePlayerRequest;
import com.back.backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @PostMapping()
    public ResponseEntity create(@RequestBody CreatePlayerRequest createPlayerRequest) {
        try {
            return ResponseEntity.ok(playerService.create(createPlayerRequest.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось создать игрока");
        }
    }
}
