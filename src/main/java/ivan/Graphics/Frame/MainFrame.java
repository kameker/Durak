package ivan.Graphics.Frame;

import ivan.Graphics.DurakSettings;
import ivan.Graphics.Panel.MainMenuPanel;

import javax.swing.*;


public class MainFrame extends JFrame {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public SettingsFrame settingsFrame = null;
    public MainFrame(DurakSettings settings) {
        MainMenuPanel mainPanel = new MainMenuPanel(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        add(mainPanel);
        mainPanel.setVisible(true);
        setVisible(true);
        mainPanel.startGame.addActionListener(e -> {
            dispose();
            settingsFrame = new SettingsFrame(settings);
        });
        mainPanel.exit.addActionListener(e -> {
            dispose();
        });
    }
}

