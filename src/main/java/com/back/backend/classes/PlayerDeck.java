package com.back.backend.classes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PlayerDeck {
    @EmbeddedId
    PlayerDeckId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    Player player;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("deckId")
    @JoinColumn(name = "deck_id")
    Deck deck;

    public PlayerDeck(PlayerDeckId id, Player player, Game game, Deck deck) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.deck = deck;
    }

    public PlayerDeck() {

    }

    public PlayerDeckId getId() {
        return id;
    }

    public void setId(PlayerDeckId id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDeck that = (PlayerDeck) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
