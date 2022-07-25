package com.back.backend.rest;

import com.back.backend.classes.Player;
import com.back.backend.rest.dto.PlayerDTO;
import com.back.backend.rest.requestsClasses.CreatePlayerRequest;
import com.back.backend.service.PlayerService;
import com.back.backend.utils.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;

    @PostMapping()
    public ResponseEntity create(@RequestBody CreatePlayerRequest createPlayerRequest) {
        try {
            Player player = playerService.create(createPlayerRequest.getName());
            PlayerDTO playerDTO = playerMapper.mapToDTO(player);

            return ResponseEntity.ok(playerDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось создать игрока");
        }
    }
}
