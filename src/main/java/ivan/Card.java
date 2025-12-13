package ivan;


import static ivan.ConsoleCard.fromById;

public class Card {
    private int number;
    private int suit;

    public Card(int suit, int number) {
        this.number = number;
        this.suit = suit;
    }

    public Card(String id) {
        // Парсим строку вида "131" или "101"
        if (id.length() == 2) {
            // Двузначные номера (2-9)
            this.number = Integer.parseInt(String.valueOf(id.charAt(0)));
            this.suit = Integer.parseInt(String.valueOf(id.charAt(1)));
        } else if (id.length() == 3) {
            // Трехзначные номера (10-14)
            if (id.startsWith("10") || id.startsWith("11") || id.startsWith("12") ||
                    id.startsWith("13") || id.startsWith("14")) {
                this.number = Integer.parseInt(id.substring(0, 2));
                this.suit = Integer.parseInt(String.valueOf(id.charAt(2)));
            } else if (id.startsWith("15")) {
                // Джокеры
                this.number = 15;
                this.suit = Integer.parseInt(String.valueOf(id.charAt(2)));
            }
        }
    }

    public ConsoleCard getConsoleCard() {
        return fromById(this.getCardsId());
    }

    public String getCardsId() {
        // Формируем ID в формате "числомасть" (например, "131" для короля пик)
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