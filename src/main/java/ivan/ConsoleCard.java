package ivan;

import java.util.ArrayList;

public enum ConsoleCard {

    // Пики (Spades 1)
    SIX_SPADE("61", 6,
            """
                    ┌─────┐
                    │6    │
                    │  1  │
                    │    6│
                    └─────┘"""),

    SEVEN_SPADE("71", 7,
            """
                    ┌─────┐
                    │7    │
                    │  1  │
                    │    7│
                    └─────┘"""),

    EIGHT_SPADE("81", 8,
            """
                    ┌─────┐
                    │8    │
                    │  1  │
                    │    8│
                    └─────┘"""),

    NINE_SPADE("91", 9,
            """
                    ┌─────┐
                    │9    │
                    │  1  │
                    │    9│
                    └─────┘"""),

    TEN_SPADE("101", 10,
            """
                    ┌─────┐
                    │10   │
                    │  1  │
                    │   10│
                    └─────┘"""),

    JACK_SPADE("111", 11,
            """
                    ┌─────┐
                    │11   │
                    │  1  │
                    │   11│
                    └─────┘"""),

    QUEEN_SPADE("121", 12,
            """
                    ┌─────┐
                    │12   │
                    │  1  │
                    │   12│
                    └─────┘"""),

    KING_SPADE("131", 13,
            """
                    ┌─────┐
                    │13   │
                    │  1  │
                    │   13│
                    └─────┘"""),

    ACE_SPADE("141", 14,
            """
                    ┌─────┐
                    │14   │
                    │  1  │
                    │   14│
                    └─────┘"""),

    // Черви (Hearts 3)
    SIX_HEART("63", 6,
            """
                    ┌─────┐
                    │6    │
                    │  3  │
                    │    6│
                    └─────┘"""),

    SEVEN_HEART("73", 7,
            """
                    ┌─────┐
                    │7    │
                    │  3  │
                    │    7│
                    └─────┘"""),

    EIGHT_HEART("83", 8,
            """
                    ┌─────┐
                    │8    │
                    │  3  │
                    │    8│
                    └─────┘"""),

    NINE_HEART("93", 9,
            """
                    ┌─────┐
                    │9    │
                    │  3  │
                    │    9│
                    └─────┘"""),

    TEN_HEART("103", 10,
            """
                    ┌─────┐
                    │10   │
                    │  3  │
                    │   10│
                    └─────┘"""),

    JACK_HEART("113", 11,
            """
                    ┌─────┐
                    │11   │
                    │  3  │
                    │   11│
                    └─────┘"""),

    QUEEN_HEART("123", 12,
            """
                    ┌─────┐
                    │12   │
                    │  3  │
                    │   12│
                    └─────┘"""),

    KING_HEART("133", 13,
            """
                    ┌─────┐
                    │13   │
                    │  3  │
                    │   13│
                    └─────┘"""),

    ACE_HEART("143", 14,
            """
                    ┌─────┐
                    │14   │
                    │  3  │
                    │   14│
                    └─────┘"""),

    SIX_DIAMOND("64", 6,
            """
                    ┌─────┐
                    │6    │
                    │  4  │
                    │    6│
                    └─────┘"""),

    SEVEN_DIAMOND("74", 7,
            """
                    ┌─────┐
                    │7    │
                    │  4  │
                    │    7│
                    └─────┘"""),

    EIGHT_DIAMOND("84", 8,
            """
                    ┌─────┐
                    │8    │
                    │  4  │
                    │    8│
                    └─────┘"""),

    NINE_DIAMOND("94", 9,
            """
                    ┌─────┐
                    │9    │
                    │  4  │
                    │    9│
                    └─────┘"""),

    TEN_DIAMOND("104", 10,
            """
                    ┌─────┐
                    │10   │
                    │  4  │
                    │   10│
                    └─────┘"""),

    JACK_DIAMOND("114", 11,
            """
                    ┌─────┐
                    │11   │
                    │  4  │
                    │   11│
                    └─────┘"""),

    QUEEN_DIAMOND("124", 12,
            """
                    ┌─────┐
                    │12   │
                    │  4  │
                    │   12│
                    └─────┘"""),

    KING_DIAMOND("134", 13,
            """
                    ┌─────┐
                    │13   │
                    │  4  │
                    │   13│
                    └─────┘"""),

    ACE_DIAMOND("144", 14,
            """
                    ┌─────┐
                    │14   │
                    │  4  │
                    │   14│
                    └─────┘"""),

    // Крести (Clubs 2)
    SIX_CLUB("62", 6,
            """
                    ┌─────┐
                    │6    │
                    │  2  │
                    │    6│
                    └─────┘"""),

    SEVEN_CLUB("72", 7,
            """
                    ┌─────┐
                    │7    │
                    │  2  │
                    │    7│
                    └─────┘"""),

    EIGHT_CLUB("82", 8,
            """
                    ┌─────┐
                    │8    │
                    │  2  │
                    │    8│
                    └─────┘"""),

    NINE_CLUB("92", 9,
            """
                    ┌─────┐
                    │9    │
                    │  2  │
                    │    9│
                    └─────┘"""),

    TEN_CLUB("102", 10,
            """
                    ┌─────┐
                    │10   │
                    │  2  │
                    │   10│
                    └─────┘"""),

    JACK_CLUB("112", 11,
            """
                    ┌─────┐
                    │11   │
                    │  2  │
                    │   11│
                    └─────┘"""),

    QUEEN_CLUB("122", 12,
            """
                    ┌─────┐
                    │12   │
                    │  2  │
                    │   12│
                    └─────┘"""),

    KING_CLUB("132", 13,
            """
                    ┌─────┐
                    │13   │
                    │  2  │
                    │   13│
                    └─────┘"""),

    ACE_CLUB("142", 14,
            """
                    ┌─────┐
                    │14   │
                    │  2  │
                    │   14│
                    └─────┘"""),

    // Джокеры (Jokers)
    BLACK_JOKER("115", 15,
            """
                    ┌─────┐
                    │BLACK│
                    │JOKER│
                    │BLACK│
                    └─────┘"""),

    RED_JOKER("315", 15,
            """
                    ┌─────┐
                    │ RED │
                    │JOKER│
                    │ RED │
                    └─────┘""");


    private final String id;
    private final int rank;
    private final String graphic;

    // Конструктор
    ConsoleCard(String id, int rank, String graphic) {
        this.id = id;
        this.rank = rank;
        this.graphic = graphic;
    }

    // Геттеры
    public String getId() {
        return id;
    }


    public int getRank() {
        return rank;
    }

    public String getGraphic() {
        return graphic;
    }

    // Получить карту по имени
    public static ConsoleCard fromById(String id) {
        for (ConsoleCard ConsoleCard : values()) {
            if (ConsoleCard.id.equals(id)) {
                return ConsoleCard;
            }
        }
        return null;
    }

    public static void printCards(ArrayList<ConsoleCard> consoleCards) {
        if (consoleCards == null || consoleCards.isEmpty()) return;

        String[] firstConsoleCardLines = consoleCards.get(0).graphic.split("\n");
        int height = firstConsoleCardLines.length;

        for (int i = 0; i < height; i++) {
            StringBuilder line = new StringBuilder();
            for (ConsoleCard consoleCard : consoleCards) {
                String[] consoleCardLines = consoleCard.graphic.split("\n");
                if (i < consoleCardLines.length) {
                    line.append(consoleCardLines[i]);
                    line.append("  ");
                }
            }
            System.out.println(line);
        }
    }


    public static void printCards(ConsoleCard... ConsoleCards) {
        if (ConsoleCards == null || ConsoleCards.length == 0) return;

        String[] firstConsoleCardLines = ConsoleCards[0].graphic.split("\n");
        int height = firstConsoleCardLines.length;

        for (int i = 0; i < height; i++) {
            StringBuilder line = new StringBuilder();
            for (ConsoleCard ConsoleCard : ConsoleCards) {
                String[] ConsoleCardLines = ConsoleCard.graphic.split("\n");
                if (i < ConsoleCardLines.length) {
                    line.append(ConsoleCardLines[i]);
                    line.append("  ");
                }
            }
            System.out.println(line);
        }
    }


    @Override
    public String toString() {
        return graphic;
    }
}
