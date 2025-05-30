
package mephi.b22901.a.l5555;


import javax.swing.*;
import java.awt.*;

/**
 * Главное окно приложения — стартовое меню игры.
 * Здесь игрок может начать новую игру или посмотреть таблицу результатов.
 * Окно содержит две кнопки: "Начать новую игру" и "Посмотреть таблицу результатов".
 */

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;

public class FirstFrame extends JFrame {

    private JButton jButtonStartGame;
    private JButton jButtonShowRecords;
    private JLabel jLabelGame;
    private JLabel jLabelGameImage;
    private JPanel jPanelStart;

    private int selectedLocations = 1;
    public static Game game = new Game();
    Human human = null;

    public FirstFrame() {
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600)); // Увеличено
        setLocationRelativeTo(null);
        initComponents();
        layoutComponents();
        setupActions();
        pack();
    }

    static Object getGame() {
        return game;
    }

    private void initComponents() {
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 18);
        Color orangeColor = new Color(255, 165, 0);

        jPanelStart = new JPanel();
        jPanelStart.setBackground(Color.BLACK);

        jButtonStartGame = new JButton("Начать новую игру");
        jButtonStartGame.setFont(buttonFont);
        jButtonStartGame.setBackground(orangeColor);
        jButtonStartGame.setForeground(Color.BLACK);

        jButtonShowRecords = new JButton("Таблица результатов");
        jButtonShowRecords.setFont(buttonFont);
        jButtonShowRecords.setBackground(orangeColor);
        jButtonShowRecords.setForeground(Color.BLACK);

   

        jLabelGameImage = new JLabel();
        URL iconUrl = this.getClass().getResource("/Logofull123.png");
        if (iconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(iconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
            jLabelGameImage.setIcon(new ImageIcon(scaledImage));
        } else {
            jLabelGameImage.setText("[Лого не найдено]");
            jLabelGameImage.setForeground(Color.RED);
        }
        jLabelGameImage.setHorizontalAlignment(SwingConstants.CENTER);
    }

   private void layoutComponents() {
    jPanelStart.setLayout(new BoxLayout(jPanelStart, BoxLayout.Y_AXIS));
    jPanelStart.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // внешние отступы

    // Центрирование всех компонентов по горизонтали
   
    jLabelGameImage.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButtonStartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButtonShowRecords.setAlignmentX(Component.CENTER_ALIGNMENT);

    jPanelStart.add(Box.createVerticalGlue());  // растягивается вверх
  
    jPanelStart.add(Box.createRigidArea(new Dimension(0, 20)));
    jPanelStart.add(jLabelGameImage);
    jPanelStart.add(Box.createRigidArea(new Dimension(0, 30)));
    jPanelStart.add(jButtonStartGame);
    jPanelStart.add(Box.createRigidArea(new Dimension(0, 20)));
    jPanelStart.add(jButtonShowRecords);
    jPanelStart.add(Box.createVerticalGlue());  // растягивается вниз

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jPanelStart, BorderLayout.CENTER);
}


    private void setupActions() {
        jButtonStartGame.addActionListener(this::onStartNewGameClicked);
        jButtonShowRecords.addActionListener(this::onShowResultsClicked);
    }

    public void onStartNewGameClicked(ActionEvent e) {
        System.out.println("Новая игра начата!");

        LocationDialog locationDialog = new LocationDialog(this);
        locationDialog.setVisible(true);

        if (locationDialog.isConfirmed()) {
            selectedLocations = locationDialog.getLocations();
            System.out.println("Игрок выбрал " + selectedLocations + " локаций.");

            human = game.NewHuman();
            startFirstLocation(human, 1);
            setVisible(false);
        }
    }

    private void startFirstLocation(Human human, int currentLocation) {
        List<Player> enemies = game.generateEnemiesForLocation(human.getLevel());
        GameFrame battleFrame = new GameFrame(human, enemies, game, currentLocation, selectedLocations);
        battleFrame.setVisible(true);
    }

    public void onShowResultsClicked(ActionEvent e) {
        ScoreboardDialog scoreboardDialog = new ScoreboardDialog(this);
        scoreboardDialog.setVisible(true);
    }
}

