package ivan;

public class DurakSettings {
    private int countOfPlayers;
    private int countOfCards;
    private boolean isReady = false;

    public int getCountOfPlayers() {
        return isCountOfPlayersIsReal() ? countOfPlayers : 2;
    }

    public int getCountOfCards() {
        return isCountOfCardsIsReal() ? countOfCards : 36;
    }

    public void setSettings(int countOfPlayers, int countOfCards) {
        this.countOfPlayers = countOfPlayers;
        this.countOfCards = countOfCards;
    }

    private boolean isCountOfCardsIsReal() {
        return countOfCards == 36 || countOfCards == 52 || countOfCards == 54;
    }

    private boolean isCountOfPlayersIsReal() {
        if (countOfPlayers < 2 || countOfPlayers > 6) return false;
        if (countOfPlayers * 6 >= countOfCards) return false;
        return true;
    }

    public void ready() {
        this.isReady = true;
    }

    public boolean isSettingsReady() {
        return isCountOfPlayersIsReal() && isCountOfCardsIsReal() && isReady;
    }

    public String settingsToString() {
        String str = "";
        str += "Игроков: " + countOfPlayers + "\n";
        str += "Карт: " + countOfCards + "\n";
        str += "Настройки готовы: " + isReady + "\n";
        return str;
    }
}
