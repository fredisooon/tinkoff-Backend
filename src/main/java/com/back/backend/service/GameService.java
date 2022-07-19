package com.back.backend.service;

import com.back.backend.classes.*;
import com.back.backend.classes.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerDeckRepository playerDeckRepository;

    private void fillPlayersDeck(Game game, List<Player> players, Deck bankDeck) {
        final int START_GAME_CARDS_COUNT = 7;

        for (Player player : players) {
            Deck playerDeck = new Deck();

            for (int j = 0; j < START_GAME_CARDS_COUNT; j++) {
                Card randomCard = deckService.extractRandomCardFromDeck(bankDeck);
                playerDeck.addCard(randomCard);
            }

            deckRepository.save(playerDeck);

            PlayerDeck playerDeckEntity = new PlayerDeck(new PlayerDeckId(game.getId(), player.getId()), playerDeck);

            playerDeckRepository.save(playerDeckEntity);
        }
    }

    public void startGame(Room room) {
        Game newGame = new Game();
        Deck bankDeck = new Deck();
        Deck gameDeck = new Deck();
        List<Player> players = room.getUsers();
        Player currentUserTurn = players.get(0);

        deckService.initializeBankDeck(bankDeck);
        deckRepository.save(gameDeck);

        Card currentCard = deckService.extractRandomCardFromDeck(bankDeck);

        newGame.setBankDeck(bankDeck);
        newGame.setGameDeck(gameDeck);
        newGame.setCurrentCard(currentCard);
        newGame.setCurrentPlayerTurn(currentUserTurn);
        newGame.setOver(false);

        gameRepository.save(newGame);

        room.setGame(newGame);
        roomRepository.save(room);

        this.fillPlayersDeck(newGame, players, bankDeck);
    }
}
