package com.back.backend.rest;


import com.back.backend.classes.Player;
import com.back.backend.exceptions.OptionalNotFoundException;
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

    // возвращает массив юзеров из БД
    @GetMapping("/list")
    public List<Player> list() {
        List<Player> playerList = playerService.listPlayer();
        return playerList;
    }


    // создаёт нового юзера по имени и присваивает ID
    @PostMapping("/user")  // вернуть на PostMapping после тестов
    public PlayerDTO create(@RequestBody String name) {     //
        Player player = playerService.create(name);
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setId(player.getId());
        return playerDTO;
    }

    // нужно ли описывать Entity для Optional<Player>, чтобы добавить туда getId getName для присвоения значений DTO?
    @GetMapping("/getuser")
    public Player getPerson(@RequestParam(value = "id") Integer id) throws OptionalNotFoundException {
        return playerService.getPlayer(id);
    }


}
