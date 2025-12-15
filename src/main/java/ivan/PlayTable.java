package ivan;

import java.util.ArrayList;

public class PlayTable {
    private ArrayList<Player> players;
    private ArrayList<Player> queueOfPlayers;
    public Player activePlayer;
    public Player attackingPlayer;
    public Player defendingPlayer;

    private Card trumpCard;
    private int minCard;
    private int maxCard;
    private Deck deck;

    public PlayTable(ArrayList<Player> players, int countOfStartCards) {
        this.players = players;
        this.minCard = countOfStartCards == 36 ? 6 : 2;
        this.maxCard = 14; // ACE
        this.deck = new Deck(minCard, maxCard, countOfStartCards);
    }

    public void initTable() {
        this.deck.generateDeck();
        this.setTrump();
        this.setCardsForPlayers();
        this.setQueueOfPlayers();
        this.activePlayer = this.queueOfPlayers.get(0);
        this.defendingPlayer = this.queueOfPlayers.get(1 % this.queueOfPlayers.size());
    }

    public Deck getDeck() {
        return deck;
    }

    public void setQueueOfPlayers() {

        this.queueOfPlayers = new ArrayList<>(this.players);
    }

    public ArrayList<Player> getQueueOfPlayers() {
        return queueOfPlayers;
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setTrump() {
        if (!this.deck.getPlayDeck().isEmpty()) {
            this.trumpCard = this.deck.getPlayDeck().get(this.deck.getPlayDeck().size() - 1);
        }
    }

    public void setCardsForPlayers() {
        for (int i = 0; i < 6; i++) {
            for (Player player : players) {
                if (!this.deck.getPlayDeck().isEmpty()) {
                    Card card = this.deck.getPlayDeck().remove(0);
                    player.addCard(card);
                }
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}