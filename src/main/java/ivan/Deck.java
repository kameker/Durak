package ivan;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> playDeck = null;
    private ArrayList<Card> defDeck = null;
    private ArrayList<Card> attDeck = null;
    private ArrayList<Card> bitDeck = null;
    private int minCard, maxCard, countOfStartCards;
    public Deck(int minCard, int maxCard, int countOfStartCards) {
        playDeck = new ArrayList<>();
        defDeck = new ArrayList<>();
        attDeck = new ArrayList<>();
        bitDeck = new ArrayList<>();
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
    public ArrayList<Card> getBitDeck() {
        return bitDeck;
    }

    public void generateDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = this.minCard; i <= this.maxCard; i++) {
            for (int j = 1; j < 5; j++) {
                cards.add(new Card(j, i));
            }
        }
        if (countOfStartCards == 54) {
            cards.add(new Card(1,15));
            cards.add(new Card(3,15));
        }
        Collections.shuffle(cards);
        playDeck =  cards;
    }

    public void updatePlayDeck(ArrayList<Card> tempCards) {
        playDeck.removeAll(tempCards);
    }
}
