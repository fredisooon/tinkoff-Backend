package com.back.backend.classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlayerDeckId implements Serializable {
    @Column(name = "deck_id")
    private Long deckId;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "player_id")
    private Long playerId;

    public PlayerDeckId(Long deckId, Long gameId, Long playerId) {
        this.deckId = deckId;
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public PlayerDeckId() {

    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
