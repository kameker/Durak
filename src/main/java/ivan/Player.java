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
    public void removeCard(Card card){
        cards.remove(card);
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
    public void addCards(ArrayList<Card> cards){
        this.cards.addAll(cards);
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
