package com.back.backend.service;

import com.back.backend.classes.*;
import com.back.backend.classes.repo.*;
import com.back.backend.exceptions.*;
import com.back.backend.rest.requestsClasses.PutCardRequest;
import com.back.backend.service.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {

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
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PlayerRepository playerRepository;


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

    private boolean isNumber(String string) {
        try {
            int number = Integer.parseInt(string);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Card extractStartCurrentCard(Deck deck) {
        boolean isNumber = false;
        Card finalCard = null;

        while (!isNumber) {
            Card randomCard = deckService.extractRandomCardFromDeck(deck);

            if (this.isNumber(randomCard.getCardValue())) {
                finalCard = randomCard;
                isNumber = true;
            } else {
                deck.addCard(randomCard);
            }
        }

        return finalCard;
    }

    public void startGame(Room room) {
        Game newGame = new Game();
        Deck bankDeck = new Deck();
        Deck gameDeck = new Deck();
        List<Player> players = room.getUsers();
        Player currentUserTurn = players.get(0);

        deckService.initializeBankDeck(bankDeck);
        deckRepository.save(gameDeck);

        Card currentCard = this.extractStartCurrentCard(bankDeck);

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

    public Game getRandomCardForPlayer(long playerId, long roomId) throws PlayerDeckNotFoundException, NoAccessException, OptionalNotFoundException {
        Player player = playerRepository.findById(playerId).get();
        Room room = roomRepository.findById(roomId).get();
        Game game = room.getGame();

        if (!Objects.equals(game.getCurrentPlayerTurn().getId(), player.getId())) {
            throw new NoAccessException("Отказано в доступе");
        }

        this.extractRandomCardFromBankDeckForPlayer(player, game, 1);
        game.setCurrentPlayerTurn(this.getOpponent(player, room.getUsers()));
        gameRepository.save(game);

        return game;
    }

    private Player getOpponent(Player currentPlayer, List<Player> allPlayers) {
        Player firstPlayer = allPlayers.get(0);
        Player secondPlayer = allPlayers.get(1);

        return Objects.equals(firstPlayer.getId(), currentPlayer.getId()) ? secondPlayer : firstPlayer;
    }

    public Game getPlayerGame (Long roomId) throws OptionalNotFoundException {
        Room room = roomRepository.findById(roomId).get();

        return room.getGame();
    }

    public Game putPlayerCard(PutCardRequest putCardRequest) throws OptionalNotFoundException, NoAccessException {
        Player player = playerRepository.findById(putCardRequest.getUserId()).get();
        Room room = roomRepository.findById(putCardRequest.getRoomId()).get();

        Card card = cardService.getCard(putCardRequest.getCardId());
        Game game = room.getGame();
        Deck playerDeck = playerDeckService.getPlayerDeck(player, game).getDeck();
        Deck gameDeck = game.getGameDeck();
        Player opponent = this.getOpponent(player, room.getUsers());

        if (!Objects.equals(player.getId(), game.getCurrentPlayerTurn().getId())) {
            throw new NoAccessException("Отказано в доступе");
        }

        if (
            !Objects.equals(game.getCurrentCard().getCardValue(), card.getCardValue()) &&
            !Objects.equals(game.getCurrentCard().getColor(), card.getColor()) &&
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

        playerDeck.removeCard(card);
        gameDeck.addCard(game.getCurrentCard());

        if (putCardRequest.getNewColor() != null &&
                (Objects.equals(card.getCardValue(), "+4") || Objects.equals(card.getCardValue(), "color"))) {
            Card newCard = cardRepository.findByCardValueAndColor(card.getCardValue(), putCardRequest.getNewColor());

            game.setCurrentCard(newCard);
        } else {
            game.setCurrentCard(card);
        }

        if (playerDeck.getCards().size() == 0) {
            game.setOver(true);
            game.setCurrentPlayerTurn(player);
        }

        deckRepository.save(gameDeck);
        deckRepository.save(playerDeck);
        cardRepository.save(card);
        gameRepository.save(game);

        return game;
    }
}
