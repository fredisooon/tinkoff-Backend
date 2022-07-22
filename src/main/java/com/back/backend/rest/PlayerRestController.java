package com.back.backend.rest;


import com.back.backend.rest.dto.PlayerDTO;
import com.back.backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/list")
    public List<PlayerDTO> list() {
        return playerService.listPlayer();
    }

    @PostMapping()
    public PlayerDTO create(@RequestBody String name) {
        return playerService.create(name);
    }

    @GetMapping("/getuser")
    public PlayerDTO getPerson(@RequestParam(value = "id") Integer id) {
        return playerService.getPersonDTO(id);
    }
}
