package ivan;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards = new ArrayList<>();
    private int playerID;
    public Player(int playerID) {
        this.playerID = playerID;
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public int getPlayerID() {
        return playerID;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public int getNumCards(){
        return cards.size();
    }
    public ArrayList<ConsoleCard> getConsoleCards(){
        ArrayList<ConsoleCard> consoleCards = new ArrayList<>();
        for (Card card : cards) {
            consoleCards.add(card.getConsoleCard());
        }
        return consoleCards;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.playerID != other.playerID) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Player{" + "cards=\n" + cards + '}';
    }
}
