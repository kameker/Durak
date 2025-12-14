package ivan;


import static ivan.ConsoleCard.fromById;

public class Card {
    private int number;
    private int suit;

    public Card(int suit, int number) {
        this.number = number;
        this.suit = suit;
    }

    public ConsoleCard getConsoleCard() {
        return fromById(this.getCardsId());
    }

    public String getCardsId() {
        return String.valueOf(number) + suit;
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
        return "Card{number=" + number + ", suit=" + suit + "}";
    }
}