package ivan;

import ivan.Graphics.Frame.GameTableFrame;
import ivan.Graphics.Frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ivan.Utils.isCardsAllNull;

public class Durak {
    private DurakSettings settings;
    private PlayTable playTable;
    private MainFrame mainFrame;

    private GameTableFrame gameTableFrame;

    private Card choosenCard = null;

    private void gameCycle() {
        while (!isSomeoneWin(this.playTable.getQueueOfPlayers())) {
            attackPhase();
            defencePhase();
            fakePhase();
            updateInfo();
        }
    }

    public void attackPhase() {

    }

    public void defencePhase() {

    }

    public void fakePhase() {

    }

    public void updateInfo() {

    }

    public boolean isSomeoneWin(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.isWin()) return true;
        }
        return false;
    }

    public Durak(DurakSettings settings, MainFrame mainFrame, GameTableFrame gameTableFrame) {
        this.settings = settings;
        this.mainFrame = mainFrame;
        this.gameTableFrame = gameTableFrame;

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < this.settings.getCountOfPlayers(); i++) {
            players.add(new Player(i));
        }
        this.playTable = new PlayTable(players, this.settings.getCountOfCards());
        this.playTable.initTable();
        gameTableFrame.gameTablePanel.setPlayer(this.playTable.activePlayer);
        initButtonsListeners();
    }

    public void initButtonsListeners() {
        //endphase
        this.gameTableFrame.gameTablePanel.endActionButton.addActionListener(e -> {
            this.playTable.nextPhaseOfGame();
        });
        //def

        for (JButton button : this.gameTableFrame.gameTablePanel.defenceCardsButtons) {
            int k = this.gameTableFrame.gameTablePanel.defenceCardsButtons.indexOf(button);
            button.addActionListener(e -> {

            });
        }
        //attack
        for (JButton button : this.gameTableFrame.gameTablePanel.attackCardsButtons) {
            int m = this.gameTableFrame.gameTablePanel.attackCardsButtons.indexOf(button);
            button.addActionListener(e -> {
                if (choosenCard != null && this.playTable.getPhaseOfGame() == 1) {
                    if (isCardsAllNull(this.playTable.attackCards)){
                        this.playTable.attackCards[m] = choosenCard;
                        choosenCard = null;
                    } else {
                        for (Card card : this.playTable.attackCards){
                            if (card != null && card.getNumber() == choosenCard.getNumber()){
                                this.playTable.attackCards[m] = choosenCard;
                                choosenCard = null;
                                break;
                            }
                        }
                    }
                }
                this.gameTableFrame.gameTablePanel.refreshCenterCards(this.playTable.attackCards,this.playTable.defenceCards);
            });
        }
        //cards
        for (JButton button : this.gameTableFrame.gameTablePanel.playerCardsButtons) {
            button.addActionListener(e -> {
                choosenCard = new Card(button.getName());
            });
        }
    }

    public static void main(String[] args) {
        DurakSettings settings = new DurakSettings();
        MainFrame mainFrame = new MainFrame(settings);
        while (!settings.isSettingsReady()) {
            System.out.println("настройки");
        }
        System.out.println(settings.settingsToString());
        Durak durak = new Durak(settings, mainFrame,
                mainFrame.settingsFrame.gameTableFrame);
        durak.gameCycle();
    }
}
