package com.back.backend.classes;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PlayerDeck {
    @EmbeddedId
    PlayerDeckId id;

    @ManyToOne(cascade = CascadeType.ALL)
    Deck deck;

    public PlayerDeck(PlayerDeckId id, Deck deck) {
        this.id = id;
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
