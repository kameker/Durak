package ivan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Robot extends Human {
    private Random random = new Random();
    private int myPlayerID; // ID этого робота

    public Robot(int playerID) {
        super(playerID);
        this.myPlayerID = playerID;
    }

    public Card chooseAttackCard(ArrayList<Card> tableCards, Card trumpCard, ArrayList<Player> allPlayers) {
        ArrayList<Card> myCards = getCards();

        if (myCards.isEmpty()) {
            return null;
        }

        // Фильтруем только валидные карты
        ArrayList<Card> validCards = new ArrayList<>();
        for (Card card : myCards) {
            if (isValidCard(card)) {
                validCards.add(card);
            }
        }

        if (validCards.isEmpty()) {
            return null;
        }

        // Стратегия 1: Если на столе нет карт
        if (tableCards.isEmpty()) {
            // Атакуем самой слабой не козырной картой
            Card weakestNonTrump = getWeakestNonTrumpCard(validCards, trumpCard);
            if (weakestNonTrump != null) {
                return weakestNonTrump;
            }
            // Если все карты козырные, атакуем самой слабой козырной
            return getWeakestTrumpCard(validCards, trumpCard);
        }

        // Стратегия 2: Если на столе есть карты
        // Сначала ищем карты того же номинала, что и на столе
        ArrayList<Card> sameRankCards = new ArrayList<>();
        for (Card tableCard : tableCards) {
            for (Card myCard : validCards) {
                if (myCard.getNumber() == tableCard.getNumber()) {
                    sameRankCards.add(myCard);
                }
            }
        }

        if (!sameRankCards.isEmpty()) {
            // Выбираем самую слабую карту из одинаковых номиналов
            return getWeakestCardFromList(sameRankCards, trumpCard);
        }

        // Стратегия 3: Оцениваем состояние защищающегося игрока
        // Находим защищающегося игрока (он должен быть передан в allPlayers)
        Player defendingPlayer = null;
        for (Player player : allPlayers) {
            if (player.getPlayerID() == (myPlayerID + 1) % allPlayers.size()) {
                defendingPlayer = player;
                break;
            }
        }

        // Если защищающийся имеет мало карт, атакуем сильнее
        if (defendingPlayer != null && defendingPlayer.getNumCards() < 3) {
            Card strategicCard = chooseCardForWeakOpponent(validCards, trumpCard);
            if (strategicCard != null) {
                return strategicCard;
            }
        }

        // Стратегия 4: Атакуем картой, которая с большой вероятностью не козырь у защищающегося
        Card strategicAttack = chooseStrategicAttackCard(tableCards, trumpCard, validCards);
        if (strategicAttack != null) {
            return strategicAttack;
        }

        // Стратегия 5: Если ничего не подходит, атакуем самой слабой не козырной картой
        Card safeAttack = getWeakestNonTrumpCard(validCards, trumpCard);
        if (safeAttack != null) {
            return safeAttack;
        }

        return getWeakestCard(validCards, trumpCard);
    }

    public Card chooseDefenseCard(Card attackCard, Card trumpCard) {
        ArrayList<Card> myCards = getCards();
        ArrayList<Card> possibleCards = new ArrayList<>();

        // Ищем все карты, которыми можно отбить
        for (Card card : myCards) {
            if (canBeatCard(card, attackCard, trumpCard)) {
                possibleCards.add(card);
            }
        }

        if (possibleCards.isEmpty()) {
            return null; // Не может отбить
        }

        // Фильтруем только валидные карты
        ArrayList<Card> validPossibleCards = new ArrayList<>();
        for (Card card : possibleCards) {
            if (isValidCard(card)) {
                validPossibleCards.add(card);
            }
        }

        if (validPossibleCards.isEmpty()) {
            return null;
        }

        // Стратегия защиты:
        // 1. Стараемся не тратить козыри без необходимости
        ArrayList<Card> nonTrumpCards = new ArrayList<>();
        ArrayList<Card> trumpCards = new ArrayList<>();

        for (Card card : validPossibleCards) {
            if (card.getSuit() == trumpCard.getSuit()) {
                trumpCards.add(card);
            } else {
                nonTrumpCards.add(card);
            }
        }

        // 2. Если можем отбить не козырем, используем его
        if (!nonTrumpCards.isEmpty()) {
            // Сначала ищем карту той же масти, что и атака
            ArrayList<Card> sameSuitCards = new ArrayList<>();
            for (Card card : nonTrumpCards) {
                if (card.getSuit() == attackCard.getSuit()) {
                    sameSuitCards.add(card);
                }
            }

            if (!sameSuitCards.isEmpty()) {
                // Используем самую слабую карту той же масти
                return getWeakestCardFromList(sameSuitCards, trumpCard);
            }

            // Иначе используем самый слабый не козырь
            return getWeakestCardFromList(nonTrumpCards, trumpCard);
        }

        // 3. Если только козыри, используем самый слабый козырь
        if (!trumpCards.isEmpty()) {
            // Но не тратим слишком сильный козырь без необходимости
            return getWeakestTrumpCardFromList(trumpCards);
        }

        // 4. Если все варианты плохи, выбираем самую слабую подходящую карту
        return getWeakestCardFromList(validPossibleCards, trumpCard);
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

    private Card getWeakestCard(ArrayList<Card> cards, Card trumpCard) {
        if (cards.isEmpty()) return null;

        Card weakest = cards.get(0);

        for (Card card : cards) {
            if (compareCards(card, weakest, trumpCard) < 0) {
                weakest = card;
            }
        }

        return weakest;
    }

    private Card getWeakestNonTrumpCard(ArrayList<Card> cards, Card trumpCard) {
        if (cards.isEmpty()) return null;

        Card weakestNonTrump = null;

        for (Card card : cards) {
            if (card.getSuit() != trumpCard.getSuit()) {
                if (weakestNonTrump == null || compareCards(card, weakestNonTrump, trumpCard) < 0) {
                    weakestNonTrump = card;
                }
            }
        }

        return weakestNonTrump;
    }

    private Card getWeakestTrumpCard(ArrayList<Card> cards, Card trumpCard) {
        if (cards.isEmpty()) return null;

        Card weakestTrump = null;

        for (Card card : cards) {
            if (card.getSuit() == trumpCard.getSuit()) {
                if (weakestTrump == null || card.getNumber() < weakestTrump.getNumber()) {
                    weakestTrump = card;
                }
            }
        }

        return weakestTrump;
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

    private Card getWeakestTrumpCardFromList(ArrayList<Card> trumpCards) {
        if (trumpCards.isEmpty()) return null;

        Card weakest = trumpCards.get(0);

        for (Card card : trumpCards) {
            if (card.getNumber() < weakest.getNumber()) {
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

    public boolean decideToAddMoreCards(ArrayList<Card> tableCards, Card trumpCard, ArrayList<Player> allPlayers, Player defendingPlayer) {
        if (tableCards.isEmpty()) return false;

        ArrayList<Card> myCards = getCards();
        if (myCards.isEmpty()) return false;

        // Проверяем, есть ли у робота карты того же номинала, что и на столе
        ArrayList<Card> possibleCards = new ArrayList<>();

        for (Card tableCard : tableCards) {
            for (Card myCard : myCards) {
                if (myCard.getNumber() == tableCard.getNumber() && isValidCard(myCard)) {
                    possibleCards.add(myCard);
                }
            }
        }

        if (possibleCards.isEmpty()) return false;

        // Умная стратегия подкидывания:
        // 1. Учитываем, сколько карт у защищающегося
        // 2. Учитываем, сколько козырей у робота
        // 3. Избегаем подкидывать, если у защищающегося мало карт (может взять и выиграть)

        int trumpCardsCount = 0;
        for (Card card : myCards) {
            if (card.getSuit() == trumpCard.getSuit()) {
                trumpCardsCount++;
            }
        }

        // Определяем, сколько карт у защищающегося
        int defendingPlayerCards = 0;
        if (defendingPlayer != null) {
            defendingPlayerCards = defendingPlayer.getNumCards();
        }

        // Высчитываем вероятность подкидывания
        double probability = 0.4; // Базовая вероятность 40%

        // Если у робота много козырей - подкидывает чаще
        if (trumpCardsCount >= 3) {
            probability += 0.2;
        }

        // Если у робота мало карт (< 3) - подкидывает реже
        if (myCards.size() <= 3) {
            probability -= 0.2;
        }

        // Если на столе уже много карт (>= 4) - подкидывает реже
        if (tableCards.size() >= 4) {
            probability -= 0.15;
        }

        // Если у защищающегося мало карт - подкидываем осторожнее
        if (defendingPlayerCards <= 2) {
            probability -= 0.15;
        }

        // Если у защищающегося много карт (> 5) - подкидываем активнее
        if (defendingPlayerCards > 5) {
            probability += 0.1;
        }

        // Если у робота есть сильные карты (тузы, короли) - подкидывает чаще
        int strongCards = 0;
        for (Card card : myCards) {
            if (card.getNumber() >= 12) { // Дамы, Короли, Тузы
                strongCards++;
            }
        }

        if (strongCards >= 2) {
            probability += 0.15;
        }

        // Ограничиваем вероятность от 10% до 80%
        probability = Math.max(0.1, Math.min(0.8, probability));

        return random.nextDouble() < probability;
    }

    private Card chooseStrategicAttackCard(ArrayList<Card> tableCards, Card trumpCard, ArrayList<Card> myCards) {
        // Сложная стратегия атаки:
        // 1. Избегаем атаковать козырями, если можно
        // 2. Предпочитаем атаковать картами масти, которой уже нет на столе
        // 3. Избегаем атаковать картами, которые могут быть легко отбиты

        // Собираем масти, которые уже есть на столе
        ArrayList<Integer> suitsOnTable = new ArrayList<>();
        for (Card card : tableCards) {
            suitsOnTable.add(card.getSuit());
        }

        // Ищем карту для атаки
        Card bestCard = null;
        int bestScore = Integer.MIN_VALUE;

        for (Card card : myCards) {
            if (!isValidCard(card)) continue;

            int score = 0;

            // Бонус за не козырную карту
            if (card.getSuit() != trumpCard.getSuit()) {
                score += 10;
            }

            // Бонус за масть, которой нет на столе
            if (!suitsOnTable.contains(card.getSuit())) {
                score += 5;
            }

            // Бонус за оптимальный номинал (8-11 - золотая середина)
            if (card.getNumber() >= 8 && card.getNumber() <= 11) {
                score += 3;
            }

            // Штраф за очень слабую карту
            if (card.getNumber() <= 7) {
                score -= 2;
            }

            // Штраф за очень сильную карту (кроме козырей)
            if (card.getNumber() >= 12 && card.getSuit() != trumpCard.getSuit()) {
                score -= 3;
            }

            if (score > bestScore) {
                bestScore = score;
                bestCard = card;
            }
        }

        return bestCard;
    }

    private Card chooseCardForWeakOpponent(ArrayList<Card> cards, Card trumpCard) {
        // Когда у противника мало карт, выбираем стратегическую карту
        Card bestCard = null;

        // Предпочитаем козыри средней силы
        for (Card card : cards) {
            if (card.getSuit() == trumpCard.getSuit() &&
                    card.getNumber() >= 9 && card.getNumber() <= 11) {
                if (bestCard == null || card.getNumber() < bestCard.getNumber()) {
                    bestCard = card;
                }
            }
        }

        if (bestCard != null) return bestCard;

        // Или сильные не козырные карты
        for (Card card : cards) {
            if (card.getSuit() != trumpCard.getSuit() && card.getNumber() >= 10) {
                if (bestCard == null || card.getNumber() < bestCard.getNumber()) {
                    bestCard = card;
                }
            }
        }

        return bestCard;
    }

    private boolean isValidCard(Card card) {
        if (card == null) return false;

        // Проверяем, что у карты есть графическое представление
        try {
            ConsoleCard consoleCard = card.getConsoleCard();
            if (consoleCard == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        // Дополнительные проверки на корректность карты
        if (card.getSuit() < 1 || card.getSuit() > 4) {
            return false;
        }

        if (card.getNumber() < 2 || card.getNumber() > 15) {
            return false;
        }

        return true;
    }

    // Метод для получения карты для подкидывания
    public Card getCardToAdd(ArrayList<Card> tableCards) {
        ArrayList<Card> myCards = getCards();

        for (Card myCard : myCards) {
            if (!isValidCard(myCard)) continue;

            for (Card tableCard : tableCards) {
                if (myCard.getNumber() == tableCard.getNumber()) {
                    return myCard;
                }
            }
        }

        return null;
    }
}