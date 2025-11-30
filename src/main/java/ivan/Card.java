package ivan;

import ivan.Graphics.IconCards;

public class Card {
    private int number;
    private int suit;
    private IconCards card;

    public Card(int suit, int number) {
        this.number = number;
        this.suit = suit;
        this.card = IconCards.fromFileName(String.valueOf(suit) + number);
    }
    public Card(String id){
        this.number = Integer.parseInt(String.valueOf(id.charAt(1)));
        this.suit = Integer.parseInt(String.valueOf(id.charAt(0)));
        System.out.println(id);
        if (id.length() == 3) this.number = Integer.parseInt(String.valueOf(id.charAt(1)) + String.valueOf(id.charAt(2)));
        this.card = IconCards.fromFileName(String.valueOf(suit) + number);
    }
    public IconCards getIconCard() {
        return card;
    }

    public String getCardsId(){
        return String.valueOf(suit) + number;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getSuit() {
        return suit;
    }
    public void setSuit(int suit) {
        this.suit = suit;
    }
    public boolean isBeating(Card other, Card trumpCard) {
        return (this.suit != other.getSuit() && this.suit == trumpCard.getSuit()) ||
                (this.suit == other.getSuit() && this.number > other.getNumber());
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.number != other.number) {
            return false;
        }
        if (this.suit != other.suit) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Card{" + "number=" + number + ", suit=" + suit + '}' + "\n";
    }
}
