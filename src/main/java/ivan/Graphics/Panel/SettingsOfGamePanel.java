package ivan.Graphics.Panel;

import javax.swing.*;
import java.awt.*;

public class SettingsOfGamePanel extends JPanel {
    private static int WIDTH, HEIGHT;
    public JButton exitButton = new JButton("Выход");
    public JButton startButton = new JButton("Начать игру");
    public JTextField countOfPlayer = new JTextField();
    public JRadioButton deck36Button = new JRadioButton("Колода 36 карт");
    public JRadioButton deck52Button = new JRadioButton("Колода 52 карты");
    public JRadioButton deck54Button = new JRadioButton("Колода 54 карты");
    public ButtonGroup deckGroup;

    public SettingsOfGamePanel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        // Настройка панели
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 40));

        // Создание группы для радиокнопок
        this.deckGroup = new ButtonGroup();
        this.deckGroup.add(deck36Button);
        this.deckGroup.add(deck52Button);
        this.deckGroup.add(deck54Button);
        deck36Button.setSelected(true);
        // Стилизация компонентов
        styleComponents();

        // Создание контейнера для элементов
        JPanel contentPanel = createContentPanel();

        // Добавление на основную панель
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        add(contentPanel, gbc);
    }

    private void styleComponents() {
        // Общие настройки шрифта
        Font customFont = new Font("Arial", Font.BOLD, 24);
        Color textColor = Color.WHITE;
        Color backgroundColor = new Color(45, 45, 60);
        Color buttonColor = new Color(70, 130, 180);
        Color hoverColor = new Color(100, 160, 210);

        // Настройка текстового поля
        countOfPlayer.setFont(customFont);
        countOfPlayer.setColumns(2);
        countOfPlayer.setText("2");
        countOfPlayer.setHorizontalAlignment(JTextField.CENTER);
        countOfPlayer.setBackground(backgroundColor);
        countOfPlayer.setForeground(textColor);
        countOfPlayer.setCaretColor(textColor);
        countOfPlayer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(buttonColor, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Настройка радиокнопок
        styleRadioButton(deck36Button, customFont, textColor);
        styleRadioButton(deck52Button, customFont, textColor);
        styleRadioButton(deck54Button, customFont, textColor);

        // Настройка кнопок
        styleButton(exitButton, customFont, buttonColor, hoverColor);
        styleButton(startButton, customFont, buttonColor, hoverColor);
    }

    private void styleRadioButton(JRadioButton button, Font font, Color textColor) {
        button.setFont(font);
        button.setForeground(textColor);
        button.setBackground(new Color(30, 30, 40));
        button.setFocusPainted(false);
        button.setIcon(createRadioIcon(false));
        button.setSelectedIcon(createRadioIcon(true));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
    }

    private Icon createRadioIcon(boolean selected) {
        int size = 25;
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (selected) {
                    g2d.setColor(new Color(70, 130, 180));
                    g2d.fillOval(x, y, size, size);
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x + 5, y + 5, size - 10, size - 10);
                } else {
                    g2d.setColor(Color.GRAY);
                    g2d.drawOval(x, y, size, size);
                }

                g2d.dispose();
            }

            @Override
            public int getIconWidth() { return size + 5; }

            @Override
            public int getIconHeight() { return size; }
        };
    }

    private void styleButton(JButton button, Font font, Color baseColor, Color hoverColor) {
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Заголовок
        JLabel title = new JLabel("Настройки игры");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, gbc);

        // Количество игроков
        JLabel playerLabel = new JLabel("Количество игроков:");
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        playerLabel.setForeground(Color.WHITE);
        panel.add(playerLabel, gbc);

        gbc.insets = new Insets(5, 0, 20, 0);
        panel.add(countOfPlayer, gbc);

        // Выбор колоды
        gbc.insets = new Insets(15, 0, 15, 0);
        JLabel deckLabel = new JLabel("Выберите колоду:");
        deckLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        deckLabel.setForeground(Color.WHITE);
        panel.add(deckLabel, gbc);

        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(deck36Button, gbc);
        panel.add(deck52Button, gbc);
        panel.add(deck54Button, gbc);

        // Кнопки
        gbc.insets = new Insets(30, 0, 10, 0);
        panel.add(startButton, gbc);

        gbc.insets = new Insets(10, 0, 5, 0);
        panel.add(exitButton, gbc);

        return panel;
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

        // Декоративные элементы
        g2d.setColor(new Color(255, 255, 255, 20));
        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            g2d.fillOval(x, y, 2, 2);
        }
    }
}