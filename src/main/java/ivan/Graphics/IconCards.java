package ivan.Graphics;

import ivan.Card;
import ivan.Player;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

public enum IconCards {
    C12 ( "12.png"),
    C13 ( "13.png"),
    C14 ( "14.png"),
    C15 ( "15.png"),
    C16 ( "16.png"),
    C17 ( "17.png"),
    C18 ( "18.png"),
    C19 ( "19.png"),
    C110("110.png"),
    C111("111.png"),
    C112("112.png"),
    C113("113.png"),
    C114("114.png"),
    C115("115.png"),

    C22 ( "22.png"),
    C23 ( "23.png"),
    C24 ( "24.png"),
    C25 ( "25.png"),
    C26 ( "26.png"),
    C27 ( "27.png"),
    C28 ( "28.png"),
    C29 ( "29.png"),
    C210("210.png"),
    C211("211.png"),
    C212("212.png"),
    C213("213.png"),
    C214("214.png"),

    C32 ( "32.png"),
    C33 ( "33.png"),
    C34 ( "34.png"),
    C35 ( "35.png"),
    C36 ( "36.png"),
    C37 ( "37.png"),
    C38 ( "38.png"),
    C39 ( "39.png"),
    C310("310.png"),
    C311("311.png"),
    C312("312.png"),
    C313("313.png"),
    C314("314.png"),
    C315("315.png"),

    C42 ( "42.png"),
    C43 ( "43.png"),
    C44 ( "44.png"),
    C45 ( "45.png"),
    C46 ( "46.png"),
    C47 ( "47.png"),
    C48 ( "48.png"),
    C49 ( "49.png"),
    C410("410.png"),
    C411("411.png"),
    C412("412.png"),
    C413("413.png"),
    C414("414.png");


    private final String fileName;
    private ImageIcon imageIcon;
    private static final Map<String, IconCards> BY_FILE_NAME = new HashMap<>();

    static {
        // Заполняем карту соответствий при загрузке класса
        for (IconCards card : values()) {
            BY_FILE_NAME.put(card.fileName, card);
        }
    }

    IconCards(String fileName) {
        this.fileName = fileName;
    }

    public ImageIcon get() {
        if (imageIcon == null) {
            String resourcePath = "/cards/" + fileName;
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                imageIcon = new ImageIcon(imgURL);
            } else {
                throw new RuntimeException("Ресурс не найден: " + resourcePath);
            }
        }
        return imageIcon;
    }

    // Получение enum по имени файла
    public static IconCards fromFileName(String fileName) {
        IconCards card = BY_FILE_NAME.get(fileName+ ".png");
        if (card == null) {
            throw new IllegalArgumentException("Неизвестное имя файла карты: " + fileName);
        }
        return card;
    }

    public String getFileName() {
        return fileName;
    }

}
