package com.back.backend.rest;


import com.back.backend.classes.Player;
import com.back.backend.rest.dto.PlayerDTO;
import com.back.backend.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    // возвращает массив юзеров из БД
    @GetMapping("/list")
    public List<Player> list() {
        List<Player> playerList = playerService.list();
        return playerList;
    }


    // создаёт нового юзера по имени и присваивает ID

    // создаёт нового юзера по имени и присваивает ID
    @PostMapping()  // вернуть на PostMapping после тестов
    public PlayerDTO createNewPlayer(@RequestBody String name) {
        Player player = playerService.create(name);
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setId(player.getId());
        return playerDTO;
    }


    @PutMapping()
    public PlayerDTO updateRoomId(@RequestBody PlayerDTO newPlayer){
        return playerService.updateRoomId(newPlayer);
    }

    // нужно ли описывать Entity для Optional<Player>, чтобы добавить туда getId getName для присвоения значений DTO?
    @GetMapping("/getuser")
    public Optional<Player> getperson(@RequestParam(value = "id") Long id) {
        //PlayerDTO playerDTO = new PlayerDTO();
        //playerDTO.setId(player.get)
        return playerService.getPlayer(id);
    }


}
