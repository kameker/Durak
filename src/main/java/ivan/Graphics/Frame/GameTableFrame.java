package ivan.Graphics.Frame;

import ivan.Card;
import ivan.DurakSettings;
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

    public GameTableFrame(DurakSettings settings) {
        this.gameTablePanel = new GameTablePanel(WIDTH, HEIGHT, settings.getCountOfPlayers(), settings.getCountOfCards());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        add(gameTablePanel);
        gameTablePanel.setVisible(true);
        setVisible(true);
        this.settings = settings;
    }
}
