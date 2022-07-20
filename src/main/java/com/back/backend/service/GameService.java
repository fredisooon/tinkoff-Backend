package com.back.backend.service;

import com.back.backend.classes.*;
import com.back.backend.classes.repo.*;
import com.back.backend.exceptions.*;
import com.back.backend.rest.dto.GameDTO;
import com.back.backend.rest.requestsClasses.PutCardRequest;
import com.back.backend.service.card.CardService;
import com.back.backend.utils.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private PlayerService playerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

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

    public void extractRandomCardFromBankDeckForPlayer(Player player, Game game, int cardsCount) throws OptionalNotFoundException {
        Deck bankDeck = game.getBankDeck();
        Deck gameDeck = game.getGameDeck();
        PlayerDeck playerDeck = playerDeckService.getPlayerDeck(player, game);

        for (int i = 0; i < cardsCount; i++) {
            if (bankDeck.getCards().size() == 0) {
                bankDeck.setCards(gameDeck.getCards());
                gameDeck.setCards(new ArrayList<>());
            }

            Card randomCard = deckService.extractRandomCardFromDeck(bankDeck);
            playerDeck.getDeck().addCard(randomCard);
        }

        deckRepository.save(bankDeck);
        deckRepository.save(gameDeck);
        playerDeckRepository.save(playerDeck);
    }

    public GameDTO getRandomCardForPlayer(long playerId, long roomId) throws PlayerDeckNotFoundException, NoAccessException, OptionalNotFoundException {
        Player player = playerService.getPlayer(playerId);
        Room room = roomService.roomById(roomId);
        Game game = room.getGame();

        if (!Objects.equals(game.getCurrentPlayerTurn().getId(), player.getId())) {
            throw new NoAccessException("Отказано в доступе");
        }

        this.extractRandomCardFromBankDeckForPlayer(player, game, 1);
        game.setCurrentPlayerTurn(this.getOpponent(player, room.getUsers()));
        gameRepository.save(game);

        return gameMapper.mapToDTO(game, player, room);
    }

    private Player getOpponent(Player currentPlayer, List<Player> allPlayers) {
        Player firstPlayer = allPlayers.get(0);
        Player secondPlayer = allPlayers.get(1);

        return Objects.equals(firstPlayer.getId(), currentPlayer.getId()) ? secondPlayer : firstPlayer;
    }

    public GameDTO putPlayerCard(PutCardRequest info) throws OptionalNotFoundException, NoAccessException {
        Player player = playerService.getPlayer(info.getUserId());
        Room room = roomService.roomById(info.getRoomId());
        Card card = cardService.getCard(info.getCardId());
        Game game = room.getGame();
        Deck playerDeck = playerDeckService.getPlayerDeck(player, game).getDeck();
        Deck gameDeck = game.getGameDeck();
        Player opponent = this.getOpponent(player, room.getUsers());

        if (!Objects.equals(player.getId(), game.getCurrentPlayerTurn().getId())) {
            throw new NoAccessException("Отказано в доступе");
        }

        if (
            !Objects.equals(game.getCurrentCard().getCardValue(), card.getCardValue()) &&
            !Objects.equals(game.getCurrentCard().getCardValue(), card.getColor()) &&
            !Objects.equals(card.getCardValue(), "+4") &&
            !Objects.equals(card.getCardValue(), "color")
        ) {
            throw new NoAccessException("Отказано в доступе");
        }

        if (
                !Objects.equals(card.getCardValue(), "reverse") &&
                !Objects.equals(card.getCardValue(), "skip") &&
                !Objects.equals(card.getCardValue(), "+2") &&
                !Objects.equals(card.getCardValue(), "+4")
        ) {
            game.setCurrentPlayerTurn(opponent);
        }

        switch (card.getCardValue()) {
            case "+2" -> this.extractRandomCardFromBankDeckForPlayer(opponent, game, 2);
            case "+4" -> this.extractRandomCardFromBankDeckForPlayer(opponent, game, 4);
        }

        if (info.getNewColor() != null &&
                (Objects.equals(card.getCardValue(), "+4") || Objects.equals(card.getCardValue(), "color"))) {
            card.setColor(info.getNewColor());
        }

        playerDeck.removeCard(card);
        gameDeck.addCard(game.getCurrentCard());
        game.setCurrentCard(card);

        if (playerDeck.getCards().size() == 0) {
            game.setOver(true);
            game.setCurrentPlayerTurn(player);
        }

        deckRepository.save(gameDeck);
        deckRepository.save(playerDeck);
        cardRepository.save(card);
        gameRepository.save(game);

        return gameMapper.mapToDTO(game, player, room);
    }
}
