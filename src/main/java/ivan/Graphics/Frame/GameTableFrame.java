package ivan.Graphics.Frame;

import ivan.Card;
import ivan.Graphics.DurakSettings;
import ivan.Graphics.IconCards;
import ivan.Graphics.Panel.GameTablePanel;
import ivan.PlayTable;
import ivan.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameTableFrame extends JFrame {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public GameTablePanel gameTablePanel = null;
    public DurakSettings settings;
    private Card selectedCard = null;
    private PlayTable playTable;

    public GameTableFrame(DurakSettings settings) {
        this.gameTablePanel = new GameTablePanel(WIDTH, HEIGHT, settings.getCountOfPlayers(), settings.getCountOfCards());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        add(gameTablePanel);
        gameTablePanel.setVisible(true);
        setVisible(true);
        this.settings = settings;
        for (Component component : this.gameTablePanel.playerCardsPanel.getComponents()) {
            if (component instanceof JButton button) {
                button.addActionListener(e -> {
                    this.selectedCard = new Card(button.getName());
                });
            }
        }

        for (JButton button : this.gameTablePanel.getCenterButtons()) {
            button.addActionListener(e -> {
                if (selectedCard != null) {
                    if (this.playTable.getPhaseOfGame() == 1) {
                        System.out.println(button.getName());
                        if (this.gameTablePanel.getCenterButtons().indexOf(button) >= 6 && button.getName() == null) {
                            button.setIcon(selectedCard.getIconCard().get());
                            button.setName(selectedCard.getCardsId());
                            selectedCard = null;
                        }
                    }
                    else if (this.playTable.getPhaseOfGame() == 2) {
                        if (this.gameTablePanel.getCenterButtons().indexOf(button) <= 7 && button.getName() == null) {
                            button.setIcon(selectedCard.getIconCard().get());
                            button.setName(selectedCard.getCardsId());
                            selectedCard = null;
                        }
                    }
                }
            });
        }
        this.gameTablePanel.endActionButton.addActionListener(e -> {
            this.playTable.nextPhaseOfGame();
            this.playTable.nextActivePlayer();
            gameTablePanel.refreshPlayersCard(this.playTable.activePlayer);
        });
        this.gameCycle();
    }

    public void gameCycle() {
        ArrayList<Player> players = new ArrayList<>();
        int countOfPlayers = settings.getCountOfPlayers();
        int countOfStartCards = settings.getCountOfCards();
        for (int i = 0; i < countOfPlayers; i++) {
            players.add(new Player(i));
        }

        this.playTable = new PlayTable(players, countOfStartCards);
        this.playTable.initTable();
        gameTablePanel.refreshPlayersCard(this.playTable.activePlayer);
        //while (!isSomeoneWin(playTable.getPlayers())) {
        //
        //}
    }

    public boolean isSomeoneWin(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.isWin()) return true;
        }
        return false;
    }
}

