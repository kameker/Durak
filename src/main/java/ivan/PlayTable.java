package ivan;
// после побития 1 из 2-ух карт завершается деф
import java.util.ArrayList;
import java.util.Collections;

public class PlayTable {
    private ArrayList<Player> players;
    private ArrayList<Player> queueOfPlayers;
    public Player activePlayer;

    private Card trumpCard;

    private int minCard;
    private int maxCard = 14;

    public Player attackingPlayer;
    public Player defendingPlayer;

    private int phaseOfGame;
    private Deck deck;

    public PlayTable(ArrayList<Player> players, int countOfStartCards) {
        this.players = players;
        this.minCard = countOfStartCards == 36 ? 6 : 2;
        this.maxCard = countOfStartCards == 36 || countOfStartCards == 52 ? 14 : 15;
        this.phaseOfGame = 1;
        this.deck = new Deck(minCard,maxCard,countOfStartCards);

    }
    public void initTable(){
        this.deck.generateDeck();
        this.setTrump();
        this.setCardsForPlayers();
        this.setQueueOfPlayers();
        this.activePlayer = this.queueOfPlayers.get(0);
    }
    public Deck getDeck() {
        return deck;
    }
    public void nextActivePlayer(){
        this.activePlayer = queueOfPlayers.get((queueOfPlayers.indexOf(this.activePlayer) + 1 ) % queueOfPlayers.size());
    }
    public void nextPhaseOfGame(){
        this.phaseOfGame = (this.phaseOfGame++) % 4;
        nextActivePlayer();
    }
    public int getPhaseOfGame() {
        return phaseOfGame;
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


    public void setTrump() {
        this.trumpCard = this.deck.getPlayDeck().get(this.deck.getPlayDeck().size() - 1);
    }

    public void setCardsForPlayers() {
        int k = 0;
        int m = 6;
        for (Player player : players) {
            for (int i = k; i < m; i++) {
                player.addCard(this.deck.getPlayDeck().get(i));
            }
            k += 6;
            m += 6;
        }
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

}