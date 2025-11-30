package ivan;

import ivan.Graphics.Panel.SettingsOfGamePanel;

public class Utils {
    public static int getCountOfPlayersFromSettingsPanel(SettingsOfGamePanel panel){
        return Integer.parseInt(panel.countOfPlayer.getText());
    }
    public static int getCountOfCardsFromSettingsPanel(SettingsOfGamePanel panel){
        return panel.deck36Button.isSelected() ? 36 : (panel.deck52Button.isSelected() ? 52 : 54);
    }
}
