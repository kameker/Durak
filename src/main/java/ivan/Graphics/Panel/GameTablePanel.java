package ivan.Graphics.Panel;

import ivan.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameTablePanel extends JPanel {
    private static int WIDTH, HEIGHT;
    private int countOfPlayers;
    private int countOfCards;
    public JPanel playerCardsPanel = new JPanel(new GridLayout(1, 6, 5, 0));
    public JPanel centerPanel;
    private ArrayList<JButton> centerButtons = new ArrayList<>();
    public JButton endActionButton;
     // Добавляем массив для кнопок центра

    public GameTablePanel(int width, int height, int countOfPlayers, int countOfCards) {
        WIDTH = width;
        HEIGHT = height;
        this.countOfPlayers = countOfPlayers;
        this.countOfCards = countOfCards;

        // Настройка панели
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 40));

        // Создание основных областей интерфейса
        createGameInterface();
    }
    public ArrayList<JButton> getCenterButtons() {
        return this.centerButtons;
    }
    private void createGameInterface() {
        // Верхняя панель - информация об игроках и козыре
        add(createTopPanel(), BorderLayout.NORTH);

        // Центральная панель - игровое поле с картами
        add(createCenterPanel(), BorderLayout.CENTER);

        // Нижняя панель - управление
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Левая часть - информация об игроках
        topPanel.add(createPlayersPanel(), BorderLayout.WEST);

        // Центральная часть - козырь и кнопка взять карту
        topPanel.add(createTrumpPanel(), BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createPlayersPanel() {
        JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        playersPanel.setOpaque(false);

        // Создаем панели для каждого игрока
        for (int i = 0; i < countOfPlayers; i++) {
            playersPanel.add(createPlayerInfoPanel(i + 1, (int) (Math.random() * 6) + 1));
        }

        return playersPanel;
    }

    private JPanel createPlayerInfoPanel(int playerNumber, int cardCount) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setOpaque(false);

        // Квадрат с номером игрока
        JLabel playerSquare = new JLabel(String.valueOf(playerNumber), SwingConstants.CENTER);
        playerSquare.setPreferredSize(new Dimension(40, 40));
        playerSquare.setMinimumSize(new Dimension(40, 40));
        playerSquare.setMaximumSize(new Dimension(40, 40));
        playerSquare.setOpaque(true);
        playerSquare.setBackground(new Color(70, 130, 180));
        playerSquare.setForeground(Color.WHITE);
        playerSquare.setFont(new Font("Arial", Font.BOLD, 20));
        playerSquare.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Метка с количеством карт
        JLabel cardCountLabel = new JLabel("Карт: " + cardCount, SwingConstants.CENTER);
        cardCountLabel.setForeground(Color.WHITE);
        cardCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        playerPanel.add(playerSquare);
        playerPanel.add(cardCountLabel);

        return playerPanel;
    }

    private JPanel createTrumpPanel() {
        JPanel trumpPanel = new JPanel();
        trumpPanel.setLayout(new BoxLayout(trumpPanel, BoxLayout.Y_AXIS));
        trumpPanel.setOpaque(false);

        // Кнопка взять карту
        JButton takeCardButton = new JButton("Взять карту");
        styleButton(takeCardButton, new Font("Arial", Font.BOLD, 16), new Color(70, 130, 180));
        takeCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Метка с количеством карт в колоде
        JLabel deckCountLabel = new JLabel("В колоде: " + countOfCards + " карт", SwingConstants.CENTER);
        deckCountLabel.setForeground(Color.WHITE);
        deckCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        deckCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Панель для козыря
        JPanel trumpSuitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        trumpSuitPanel.setOpaque(false);

        JLabel trumpLabel = new JLabel("Козырь:");
        trumpLabel.setForeground(Color.WHITE);
        trumpLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Квадрат с козырной мастью (пример с пиками)
        JLabel trumpSuit = new JLabel("♠", SwingConstants.CENTER);
        trumpSuit.setPreferredSize(new Dimension(30, 30));
        trumpSuit.setOpaque(true);
        trumpSuit.setBackground(Color.WHITE);
        trumpSuit.setForeground(Color.BLACK);
        trumpSuit.setFont(new Font("Arial", Font.BOLD, 20));
        trumpSuit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        trumpSuitPanel.add(trumpLabel);
        trumpSuitPanel.add(trumpSuit);

        trumpPanel.add(takeCardButton);
        trumpPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        trumpPanel.add(deckCountLabel);
        trumpPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        trumpPanel.add(trumpSuitPanel);

        return trumpPanel;
    }

    private JPanel createCenterPanel() {
        this.centerPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        this.centerPanel.setOpaque(false);
        this.centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        this.centerPanel.setBackground(new Color(255, 0, 0, 50)); // Временно для отладки

        // Создаем 12 кнопок для игрового поля
        for (int i = 0; i < 12; i++) {
            JButton fieldCardButton = new JButton(); // Временно добавляем текст для видимости
            styleCardButton(fieldCardButton);
            centerButtons.add(i,fieldCardButton); // Сохраняем ссылку на кнопку
            this.centerPanel.add(fieldCardButton);
        }

        return this.centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Левая кнопка - завершить кон
        JButton endTurnButton = new JButton("Завершить кон");
        styleButton(endTurnButton, new Font("Arial", Font.BOLD, 16), new Color(200, 80, 80));
        bottomPanel.add(endTurnButton, BorderLayout.WEST);

        // Центральные кнопки - карты игрока
        this.playerCardsPanel.setOpaque(false);
        for (int i = 0; i < 6; i++) {
            JButton playerCard = new JButton("P" + (i + 1)); // Временно добавляем текст для видимости
            styleCardButton(playerCard);
            this.playerCardsPanel.add(playerCard);
        }

        bottomPanel.add(playerCardsPanel, BorderLayout.CENTER);

        // Правая кнопка - завершить действие
        this.endActionButton = new JButton("Завершить действие");
        styleButton(this.endActionButton, new Font("Arial", Font.BOLD, 16), new Color(80, 160, 100));
        bottomPanel.add(this.endActionButton, BorderLayout.EAST);

        return bottomPanel;
    }

    private void styleButton(JButton button, Font font, Color backgroundColor) {
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
    }

    private void styleCardButton(JButton button) {
        // Временно оставляем видимыми стили для отладки
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setOpaque(true);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        // Устанавливаем размеры для лучшей видимости
        button.setPreferredSize(new Dimension(100, 145));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Градиентный фон
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(30, 30, 40),
                WIDTH, HEIGHT, new Color(50, 50, 70)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Декоративные элементы (звезды)
        g2d.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 30; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            int size = 1 + (int) (Math.random() * 3);
            g2d.fillOval(x, y, size, size);
        }
    }

    public void refreshPlayersCard(Player player) {
        int k = 0;
        for (Component c : playerCardsPanel.getComponents()) {
            if (c instanceof JButton button) {
                if (k < player.getCards().size()) {
                    // Масштабируем иконку до нужного размера
                    ImageIcon originalIcon = player.getCards().get(k).getIconCard().get();
                    if (originalIcon != null) {
                        Image scaledImage = originalIcon.getImage().getScaledInstance(
                                100, 145, Image.SCALE_SMOOTH
                        );
                        button.setIcon(new ImageIcon(scaledImage));
                        button.setText(""); // Убираем текст когда есть иконка
                    }
                    button.setName(player.getCards().get(k).getCardsId());
                } else {
                    button.setIcon(null); // Очищаем иконку, если карты нет
                    button.setText("P" + (k + 1)); // Возвращаем текст
                    button.setName("");
                }
            }
            k++;
        }
    }

    // Метод для обновления карт в центре стола
    public void refreshCenterCards(java.util.List<ivan.Card> cards) {
        for (int i = 0; i < centerButtons.size(); i++) {
            JButton button = centerButtons.get(i);
            if (button != null) {
                if (i < cards.size() && cards.get(i) != null) {
                    // Масштабируем иконку до нужного размера
                    ImageIcon originalIcon = cards.get(i).getIconCard().get();
                    if (originalIcon != null) {
                        Image scaledImage = originalIcon.getImage().getScaledInstance(
                                100, 145, Image.SCALE_SMOOTH
                        );
                        button.setIcon(new ImageIcon(scaledImage));
                        button.setText(""); // Убираем текст когда есть иконка
                    }
                    button.setName(cards.get(i).getCardsId());
                } else {
                    button.setIcon(null); // Очищаем иконку, если карты нет
                    button.setText("C" + (i + 1)); // Возвращаем текст
                    button.setName("");
                }
            }
        }
    }

    // Метод для получения кнопки центральной панели по индексу
    public JButton getCenterButton(int index) {
        if (index >= 0 && index < centerButtons.size()) {
            return centerButtons.get(index);
        }
        return null;
    }

    public void paintMyComponent(Graphics g) {
        paintComponent(g);
    }
}