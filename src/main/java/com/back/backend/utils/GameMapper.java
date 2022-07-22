package com.back.backend.utils;

import com.back.backend.classes.*;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.rest.dto.CardDTO;
import com.back.backend.rest.dto.GameDTO;
import com.back.backend.rest.dto.OpponentDTO;
import com.back.backend.service.PlayerDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class GameMapper {
    @Autowired
    PlayerDeckService playerDeckService;

    public GameDTO mapToDTO(Game game, Player player, Room room) throws OptionalNotFoundException {
        GameDTO gameDTO = new GameDTO();
        CardDTO cardDTO = CardMapper.mapToDTO(game.getCurrentCard());

        Deck playerDeck = this.playerDeckService.getPlayerDeck(player, game).getDeck();
        List<CardDTO> cards = CardMapper.listToDTO(playerDeck.getCards());
        List<Player> players = room.getUsers();
        OpponentDTO opponentDTO = this.getOpponentForPlayer(player, players, game);

        gameDTO.setId(game.getId());
        gameDTO.setCurrentCard(cardDTO);
        gameDTO.setOver(game.isOver());
        gameDTO.setCurrentPlayerTurnId(game.getCurrentPlayerTurn().getId());
        gameDTO.setUserCards(cards);
        gameDTO.setOpponent(opponentDTO);

        return gameDTO;
    }

    private OpponentDTO getOpponentForPlayer(Player player, List<Player> opponents, Game game) throws OptionalNotFoundException {
        OpponentDTO opponentDTO = new OpponentDTO();

        for (Player opponent: opponents) {
            if (!Objects.equals(opponent.getId(), player.getId())) {
                PlayerDeck opponentDeck = playerDeckService.getPlayerDeck(opponent, game);

                opponentDTO.setId(opponent.getId());
                opponentDTO.setName(opponent.getName());
                opponentDTO.setCardCount(opponentDeck.getDeck().getCards().size());
                break;
            }
        }

        return opponentDTO;
    }
}