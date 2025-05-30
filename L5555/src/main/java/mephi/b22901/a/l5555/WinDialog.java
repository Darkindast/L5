/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Collections;

/**
 * Диалоговое окно, которое появляется при победе игрока на локации.
 * Показывает сообщение о победе и кнопку "Дальше" для перехода к следующей локации
 * или завершению игры с выводом результата и возможностью занести его в топ-10.
 */
public class WinDialog extends JDialog {

    private JButton btnNext;
    private GameFrame parentFrame;
    
    public WinDialog(GameFrame parentFrame) {
        super(parentFrame, "Победа!", true);
        this.parentFrame = parentFrame;
        setSize(500, 200);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }
    
    /**
     * Создание и размещение компонентов: метка с сообщением и кнопка.
     */
private void initializeComponents() {
    // Основная панель с черным фоном
    JPanel mainPanel = new JPanel(new BorderLayout(0, 20)); // Добавляем вертикальные отступы
    mainPanel.setBackground(new Color(0, 0, 0));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Внешние отступы

    // Сообщение о победе (стилизованное)
    JLabel messageLabel = new JLabel(
        "Вы одержали победу в локации №" + parentFrame.getCurrentLocation() + 
        " из " + parentFrame.getTotalLocations() + " !", 
        SwingConstants.CENTER
    );
    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
    messageLabel.setForeground(new Color(200, 0, 0));

    // Центральная панель для изображения
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(new Color(0, 0, 0));

    // Изображение победы
    try {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Win_Round.gif"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT);
        JLabel winImage = new JLabel(new ImageIcon(scaledImage));
        winImage.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(winImage, BorderLayout.CENTER);
    } catch (Exception e) {
        JLabel noImageLabel = new JLabel("ПОБЕДА!", SwingConstants.CENTER);
        noImageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        noImageLabel.setForeground(new Color(255, 215, 0));
        centerPanel.add(noImageLabel, BorderLayout.CENTER);
    }

    // Кнопка "Дальше" (стилизованная)
    btnNext = new JButton("ДАЛЬШЕ");
    btnNext.setBackground(new Color(200, 0, 0));
    btnNext.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
    btnNext.setForeground(new Color(255, 215, 0));
    btnNext.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
    btnNext.setFocusPainted(false);
    btnNext.setPreferredSize(new Dimension(200, 40));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(0, 0, 0));
    buttonPanel.add(btnNext);

    // Компоновка элементов
    mainPanel.add(messageLabel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Установка основной панели
    add(mainPanel);
    
    // Настройки окна
    pack(); // Автоматически подбирает размер под содержимое
    setLocationRelativeTo(null); // Центрирует окно на экране
    setResizable(false); // Запрещает изменять размер окна
}
    private void setupActions() {
        btnNext.addActionListener(this::onNextClicked);
    }
    
    /**
     * Логика при нажатии кнопки "Дальше":
     * Если есть еще локации — создается новое окно битвы с новой локацией.
     * Если все локации пройдены — отображается итог и возможность сохранить результат.
     */
public void onNextClicked(ActionEvent e) {
    dispose();
    if (parentFrame.getCurrentLocation() < parentFrame.getTotalLocations()) {
        if (parentFrame.getCurrentLocation() < parentFrame.getTotalLocations()) {
            parentFrame.getHuman().setDamage(parentFrame.getHuman().getMaxDamage());
            GameFrame nextBattle = new GameFrame(
            parentFrame.getHuman(), 
            parentFrame.getGame().generateEnemiesForLocation(parentFrame.getHuman().getLevel()), 
            parentFrame.getGame(), 
            parentFrame.getCurrentLocation() + 1, 
            parentFrame.getTotalLocations());
            nextBattle.setVisible(true);
        } 
    } else {
        int playerScore = parentFrame.getHuman().getPoints();
        int position = getTop10Position(playerScore)+1;

        if (position == -1) {
            JOptionPane.showMessageDialog(null, 
                "Вы прошли все локации!\nК сожалению, вы не попали в топ-10.\nВаш результат: " + playerScore + " очков.",
                "Конец игры", JOptionPane.INFORMATION_MESSAGE);
        } else {
            
            
            List<Integer> topScores = ExcelProvider.loadTop10ScoresFromExcel();

            
            JTextField nameField = new JTextField();
            Object[] message = {
                "Поздравляем!\nВы попали в топ-10 на позицию #" + position + "!",
                "Ваши очки: " + playerScore,
                "Введите ваше имя:", nameField
            };
            
            int option = JOptionPane.showConfirmDialog(null, message, "Топ-10", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    ExcelProvider.writeToExcel(playerName, playerScore);
                    JOptionPane.showMessageDialog(null, "Результат сохранён! Спасибо за игру!");
                } else {
                    JOptionPane.showMessageDialog(null, "Имя не введено. Результат не сохранён.");
                }
            }
        }
        MainFrame mainMenu = new MainFrame();
        mainMenu.setVisible(true);
    }
}
    
     /**
     * Метод определения позиции игрока в топ-10 по результатам.
     * Возвращает позицию (1..10) или -1 если игрок не попадает в топ.
     * 
     * @param playerScore текущий результат игрока
     * @return позиция в топ-10, или -1 если нет
     */
   private int getTop10Position(int playerScore) {
    List<Integer> topScores = ExcelProvider.loadTop10ScoresFromExcel();
    
    // Если таблица пустая, сразу возвращаем 1 (первая позиция)
    if (topScores.isEmpty()) {
        return 1;
    }
    
    int minScore = getMin(topScores);
    int position = -1; // По умолчанию - не в топ-10
    
    // Если результат игрока больше минимального в топе
    if (playerScore > minScore) {
        // Ищем позицию для вставки
        for (int i = 0; i < topScores.size(); i++) {
            if (playerScore > topScores.get(i)) {
                position = i + 1; // Позиции нумеруются с 1
                break;
            }
        }
        
        // Если не нашли позицию (должен быть в конце списка)
        if (position == -1) {
            position = topScores.size() + 1;
        }
    }
    
    return position;
}
    
    /**
     * Получить минимальное значение из списка.
     * @param list список чисел
     * @return минимальное значение
     * @throws IllegalArgumentException если список пустой или null
     */
    public int getMin(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список пуст или null");
        }
        return Collections.min(list);
    }
}