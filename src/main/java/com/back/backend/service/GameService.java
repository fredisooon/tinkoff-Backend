package com.back.backend.service;

import com.back.backend.classes.*;
import com.back.backend.classes.repo.*;
import com.back.backend.exceptions.*;
import com.back.backend.rest.dto.GameDTO;
import com.back.backend.utils.GameMapper;
import com.back.backend.utils.OptionalWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private PlayerDeckService playerDeckService;

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private RoomService roomService;
    @Autowired
    private PlayerService playerService;

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

    public GameDTO getRandomCardForPlayer(long playerId, long roomId) throws PlayerDeckNotFoundException, NoAccessException, OptionalNotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);

        OptionalWorker.checkOptional(roomOptional);
        OptionalWorker.checkOptional(playerOptional);

        Room room = roomOptional.get();
        Player player = playerOptional.get();
        Game game = room.getGame();

        if (!Objects.equals(game.getCurrentPlayerTurn().getId(), player.getId())) {
            throw new NoAccessException("Отказано в доступе");
        }

        Deck bankDeck = game.getBankDeck();
        Deck gameDeck = game.getGameDeck();

        if (bankDeck.getCards().size() == 0) {
            bankDeck.setCards(gameDeck.getCards());
            gameDeck.setCards(new ArrayList<>());
        }

        Card randomCard = deckService.extractRandomCardFromDeck(bankDeck);
        PlayerDeck playerDeck = playerDeckService.getPlayerDeck(player, game);
        playerDeck.getDeck().addCard(randomCard);
        game.setCurrentPlayerTurn(this.getOpponent(player, room.getUsers()));

        deckRepository.save(bankDeck);
        deckRepository.save(gameDeck);
        playerDeckRepository.save(playerDeck);
        gameRepository.save(game);

        return gameMapper.mapToDTO(game, player, room);
    }

    private Player getOpponent(Player currentPlayer, List<Player> allPlayers) {
        Player firstPlayer = allPlayers.get(0);
        Player secondPlayer = allPlayers.get(1);

        return Objects.equals(firstPlayer.getId(), currentPlayer.getId()) ? secondPlayer : firstPlayer;
    }

    public GameDTO getPlayerGame (Long userId, Long roomId) throws OptionalNotFoundException {
        Room room = roomService.roomById(roomId);
        Game game = room.getGame();
        Player player = playerService.getPerson(userId);

        return gameMapper.mapToDTO(game, player, room);
    }
}
