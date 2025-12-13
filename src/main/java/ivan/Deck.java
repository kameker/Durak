package ivan;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> playDeck = null;
    private ArrayList<Card> defDeck = null;
    private ArrayList<Card> attDeck = null;
    private int minCard, maxCard;

    public Deck(int minCard, int maxCard, int countOfStartCards) {
        playDeck = new ArrayList<>();
        defDeck = new ArrayList<>();
        attDeck = new ArrayList<>();
        this.minCard = minCard;
        this.maxCard = maxCard;
    }

    public ArrayList<Card> getPlayDeck() {
        return playDeck;
    }

    public ArrayList<Card> getDefDeck() {
        return defDeck;
    }

    public ArrayList<Card> getAttDeck() {
        return attDeck;
    }

    public void addCardToAttDeck(Card card) {
        attDeck.add(card);
    }

    public void addCardToDefDeck(Card card) {
        defDeck.add(card);
    }

    public ArrayList<Card> getActiveCards() {
        ArrayList<Card> activeCards = new ArrayList<>();
        activeCards.addAll(attDeck);
        activeCards.addAll(defDeck);
        return activeCards;
    }

    public void generateDeck() {
        ArrayList<Card> cards = new ArrayList<>();

        // Создаем карты от minCard до maxCard
        for (int number = this.minCard; number <= this.maxCard; number++) {
            for (int suit = 1; suit <= 4; suit++) {
                cards.add(new Card(suit, number));
            }
        }

        // Добавляем джокеров для колоды в 54 карты
        if (this.maxCard == 14 && cards.size() == 52) { // Если это колода в 52 карты
            cards.add(new Card(1, 15)); // Черный джокер
            cards.add(new Card(3, 15)); // Красный джокер
        }

        Collections.shuffle(cards);
        playDeck = cards;
    }
}