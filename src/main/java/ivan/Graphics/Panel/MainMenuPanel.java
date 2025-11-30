package ivan.Graphics.Panel;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private static int WIDTH, HEIGHT;
    public JButton startGame = new JButton("Начать игру");
    public JButton exit = new JButton("Выход");

    public MainMenuPanel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        // Настройка панели
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 40));

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
        Font buttonFont = new Font("Arial", Font.BOLD, 28);
        Color buttonColor = new Color(70, 130, 180);

        // Настройка кнопок
        styleButton(startGame, buttonFont, buttonColor);
        styleButton(exit, buttonFont, new Color(180, 70, 70));
    }

    private void styleButton(JButton button, Font font, Color backgroundColor) {
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        button.setFocusPainted(false);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 40, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Заголовок игры
        JLabel title = new JLabel("Дурак");
        title.setFont(new Font("Arial", Font.BOLD, 72));
        title.setForeground(new Color(255, 215, 0)); // Золотой цвет
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Эффект тени для заголовка
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(title, gbc);

        // Подзаголовок
        JLabel subtitle = new JLabel("Карточная игра");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 24));
        subtitle.setForeground(new Color(200, 200, 200));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        panel.add(subtitle, gbc);

        // Кнопки меню
        gbc.insets = new Insets(15, 0, 15, 0);
        panel.add(startGame, gbc);

        gbc.insets = new Insets(30, 0, 10, 0);
        panel.add(exit, gbc);

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

        // Декоративные элементы (звезды)
        g2d.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 30; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            int size = 1 + (int) (Math.random() * 3);
            g2d.fillOval(x, y, size, size);
        }
    }

    public void paintMyComponent(Graphics g) {
        // Оставлен для обратной совместимости, но теперь используется paintComponent
        paintComponent(g);
    }
}