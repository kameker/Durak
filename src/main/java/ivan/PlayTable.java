package ivan;

import java.util.ArrayList;
import java.util.Collections;

public class PlayTable {
    public ArrayList<Card> freeCards;

    private ArrayList<Card> startCards;
    private ArrayList<Card> activeCards;
    private ArrayList<Card> bitCards;
    private ArrayList<Player> players;
    private ArrayList<Player> queueOfPlayers;
    private ArrayList<Card> playersCards;

    public Player attackPlayer;
    public Player defensePlayer;

    public Player activePlayer;

    private Card trumpCard;

    private int countOfStartCards;
    private int minCard;
    private int maxCard = 14;

    private int phaseOfGame;

    public PlayTable(ArrayList<Player> players, int countOfStartCards) {
        this.players = players;
        this.countOfStartCards = countOfStartCards;
        this.minCard = countOfStartCards == 36 ? 6 : 2;
        //this.maxCard = countOfStartCards == 36 || countOfStartCards == 52 ? 14 : 15;
        this.startCards = genRandomCards();
        this.activeCards = new ArrayList<>();
        this.bitCards = new ArrayList<>();
        this.phaseOfGame = 1;

    }
    public void initTable(){
        this.genRandomCards();
        this.setTrump();
        this.setCardsForPlayers();
        this.setQueueOfPlayers();
        this.setFreeCards();
        this.attackPlayer = this.queueOfPlayers.get(0);
        this.defensePlayer = this.queueOfPlayers.get(1);
        this.activePlayer = this.attackPlayer;
    }
    public void nextActivePlayer(){
        this.activePlayer = this.defensePlayer;
    }
    public void nextPhaseOfGame(){
        this.phaseOfGame = (this.phaseOfGame++) % 4;
    }
    public int getPhaseOfGame() {
        return phaseOfGame;
    }
    public void addCardToActiveCards(Card card) {
        this.activeCards.add(card);
    }
    public void clearPlayTable() {
        this.bitCards.addAll(activeCards);
        this.activeCards.clear();
    }
    public void setFreeCards() {
        setPlayersCards();
        ArrayList<Card> fCards = new ArrayList<>();
         for (Card card : this.startCards) {
             if (!this.playersCards.contains(card)) {
                fCards.add(card);
             }
         }
         this.freeCards = fCards;
    }

    public void setPlayersCards(){
        ArrayList<Card> playersCards = new ArrayList<>();
        for (Player player : players) {
            playersCards.addAll(player.getCards());
        }
        this.playersCards = playersCards;
    }
    public void setQueueOfPlayers() {
        ArrayList<Player> qPlayers = new ArrayList<>();
        Player firstPlayer = null;
        int tempNum = this.trumpCard.getNumber();
        boolean high = true;

        if (this.trumpCard.getNumber() >= 11) high = false;
        for (Player player : players) {
            for (Card card : player.getCards()) {
                if (card.getSuit() == this.trumpCard.getSuit()) {
                    int cardNumber = card.getNumber();
                    if ((high && cardNumber >= tempNum) || (!high && cardNumber <= tempNum)) {
                        tempNum = cardNumber;
                        firstPlayer = player;
                    }
                }
            }
        }
        if (firstPlayer == null) {
            this.queueOfPlayers = this.players;
            return;
        }
        qPlayers.add(firstPlayer);
        for (int i = 0; i < this.players.size(); i++) {
            if (!players.get(i).equals(firstPlayer)) {
                qPlayers.add(this.players.get(i));
            }
        }
        this.queueOfPlayers = qPlayers;
    }
    public ArrayList<Player> getQueueOfPlayers() {
        return queueOfPlayers;
    }
    public Card getTrumpCard() {
        return trumpCard;
    }
    public ArrayList<Card> genRandomCards() {
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
        return cards;
    }

    public void setTrump() {
        this.trumpCard = this.startCards.get(this.startCards.size() - 1);
    }

    public void setCardsForPlayers() {
        int k = 0;
        int m = 6;
        for (Player player : players) {
            for (int i = k; i < m; i++) {
                player.addCard(startCards.get(i));
            }
            k += 6;
            m += 6;
        }
    }


    public ArrayList<Card> getStartCards() {
        return startCards;
    }

    public ArrayList<Card> getActiveCards() {
        return activeCards;
    }

    public ArrayList<Card> getBitCards() {
        return bitCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setStartCards(ArrayList<Card> startCards) {
        this.startCards = startCards;
    }
}
