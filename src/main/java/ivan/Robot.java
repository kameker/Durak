package ivan;

import java.util.ArrayList;
import java.util.Random;

public class Robot extends Human {
    private Random random = new Random();

    public Robot(int playerID) {
        super(playerID);
    }

    public Card chooseAttackCard(ArrayList<Card> tableCards, Card trumpCard) {
        ArrayList<Card> myCards = getCards();

        if (myCards.isEmpty()) {
            return null;
        }

        // Если на столе нет карт, атакуем самой слабой картой
        if (tableCards.isEmpty()) {
            return getWeakestCard(trumpCard);
        }

        // Если на столе есть карты, ищем карту того же номинала
        for (Card tableCard : tableCards) {
            for (Card myCard : myCards) {
                if (myCard.getNumber() == tableCard.getNumber()) {
                    return myCard;
                }
            }
        }

        // Если не нашли одинаковых, атакуем самой слабой картой
        return getWeakestCard(trumpCard);
    }

    public Card chooseDefenseCard(Card attackCard, Card trumpCard) {
        ArrayList<Card> myCards = getCards();
        ArrayList<Card> possibleCards = new ArrayList<>();

        // Ищем карты, которыми можно отбить
        for (Card card : myCards) {
            if (canBeatCard(card, attackCard, trumpCard)) {
                possibleCards.add(card);
            }
        }

        if (possibleCards.isEmpty()) {
            return null; // Не может отбить
        }

        // Выбираем самую слабую подходящую карту
        return getWeakestCardFromList(possibleCards, trumpCard);
    }

    private boolean canBeatCard(Card defenseCard, Card attackCard, Card trumpCard) {
        // Если карты одной масти - защита должна быть выше
        if (defenseCard.getSuit() == attackCard.getSuit()) {
            return defenseCard.getNumber() > attackCard.getNumber();
        }

        // Если защита козырем, а атака не козырь - можно бить
        if (defenseCard.getSuit() == trumpCard.getSuit() &&
                attackCard.getSuit() != trumpCard.getSuit()) {
            return true;
        }

        return false;
    }

    private Card getWeakestCard(Card trumpCard) {
        ArrayList<Card> myCards = getCards();
        if (myCards.isEmpty()) return null;

        Card weakest = myCards.get(0);

        for (Card card : myCards) {
            if (compareCards(card, weakest, trumpCard) < 0) {
                weakest = card;
            }
        }

        return weakest;
    }

    private Card getWeakestCardFromList(ArrayList<Card> cards, Card trumpCard) {
        if (cards.isEmpty()) return null;

        Card weakest = cards.get(0);

        for (Card card : cards) {
            if (compareCards(card, weakest, trumpCard) < 0) {
                weakest = card;
            }
        }

        return weakest;
    }

    private int compareCards(Card card1, Card card2, Card trumpCard) {
        // Козыри сильнее всех
        boolean isTrump1 = card1.getSuit() == trumpCard.getSuit();
        boolean isTrump2 = card2.getSuit() == trumpCard.getSuit();

        if (isTrump1 && !isTrump2) return 1;
        if (!isTrump1 && isTrump2) return -1;

        // Если обе карты козыри или обе не козыри, сравниваем по номиналу
        if (isTrump1 && isTrump2) {
            return Integer.compare(card1.getNumber(), card2.getNumber());
        }

        // Если масти разные и обе не козыри, сравниваем только по номиналу
        if (card1.getSuit() == card2.getSuit()) {
            return Integer.compare(card1.getNumber(), card2.getNumber());
        }

        // Разные масти, не козыри - считаем равными для простоты
        return Integer.compare(card1.getNumber(), card2.getNumber());
    }

    public boolean decideToAddMoreCards(ArrayList<Card> tableCards) {
        if (tableCards.isEmpty()) return false;

        ArrayList<Card> myCards = getCards();
        if (myCards.isEmpty()) return false;

        // Проверяем, есть ли у робота карты того же номинала, что и на столе
        for (Card tableCard : tableCards) {
            for (Card myCard : myCards) {
                if (myCard.getNumber() == tableCard.getNumber()) {
                    // Вероятность подкидывания зависит от количества карт
                    if (myCards.size() <= 3) {
                        return random.nextDouble() < 0.3; // 30% если мало карт
                    } else if (myCards.size() <= 5) {
                        return random.nextDouble() < 0.5; // 50% если среднее количество
                    } else {
                        return random.nextDouble() < 0.7; // 70% если много карт
                    }
                }
            }
        }

        return false;
    }
}