package com.back.backend.service;

import com.back.backend.classes.Card;
import com.back.backend.classes.Deck;
import com.back.backend.classes.repo.CardRepository;
import com.back.backend.classes.repo.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeckService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    DeckRepository deckRepository;

    public void initializeBankDeck(Deck deck) {
        List<Card> allCards = cardRepository.findAll();

        deck.setCards(allCards);
        deckRepository.save(deck);
    }

    public Card extractRandomCardFromDeck(Deck deck) {
        int randomValue = this.getRandomValue(0, deck.getCards().size() - 1);
        Card randomCard = deck.getCards().get(randomValue);

        deck.removeCard(randomCard);
        deckRepository.save(deck);

        return randomCard;
    }

    private int getRandomValue(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }
}
