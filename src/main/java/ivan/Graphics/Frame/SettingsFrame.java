package ivan.Graphics.Frame;

import ivan.DurakSettings;
import ivan.Graphics.Panel.SettingsOfGamePanel;
import ivan.Utils;

import javax.swing.*;


public class SettingsFrame extends JFrame {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public SettingsOfGamePanel settingsOfGamePanel;
    public GameTableFrame gameTableFrame;
    public SettingsFrame(DurakSettings settings) {
        this.settingsOfGamePanel = new SettingsOfGamePanel(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        add(settingsOfGamePanel);
        settingsOfGamePanel.setVisible(true);
        setVisible(true);
        settingsOfGamePanel.exitButton.addActionListener(e -> {
            dispose();
        });
        settingsOfGamePanel.startButton.addActionListener(e -> {
            settings.setSettings(Utils.getCountOfPlayersFromSettingsPanel(this.settingsOfGamePanel),
                    Utils.getCountOfCardsFromSettingsPanel(this.settingsOfGamePanel));
            this.gameTableFrame = new GameTableFrame(settings);
            settings.ready();
            dispose();
        });
    }
}
