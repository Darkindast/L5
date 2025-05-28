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
 * 
 * @author Арсений
 */
public class WinDialog extends JDialog {

    private JButton btnNext;
    private BattleFrame parentFrame;
    
    public WinDialog(BattleFrame parentFrame) {
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
        JLabel messageLabel = new JLabel("Вы одержали победу в локации №" + parentFrame.getCurrentLocation() + " из " + parentFrame.getTotalLocations() + " !", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNext = new JButton("Дальше");
        buttonPanel.add(btnNext);
        add(buttonPanel, BorderLayout.SOUTH);
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
            parentFrame.getHuman().setDamage(parentFrame.getHuman().getMaxDamage());
            BattleFrame nextBattle = new BattleFrame(
                    parentFrame.getHuman(), 
                    parentFrame.getGame().generateEnemiesForLocation(parentFrame.getHuman().getLevel()), 
                    parentFrame.getGame(), 
                    parentFrame.getCurrentLocation() + 1, 
                    parentFrame.getTotalLocations());
            nextBattle.setVisible(true);
        } else {
            int playerScore = parentFrame.getHuman().getPoints();
            int position = getTop10Position(playerScore);

            if (position == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Вы прошли все локации!\nК сожалению, вы не попали в топ-10.\nВаш результат: " + playerScore + " очков.",
                    "Конец игры", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
                        ExcelManager.writeToExcel(playerName, playerScore); // вызов без реализации
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
        List<Integer> topScores = ExcelManager.loadTop10ScoresFromExcel();
        int position = 0;
        if (playerScore > getMin(topScores)){
            for (int i = 0; i < topScores.size(); i++) {
                if (playerScore > topScores.get(i)) {
                    position = i + 1;
                    break;
                }
            }
            if (position == -1) {
                position = topScores.size() + 1;
            }
        } else {
            position = -2;
        }
        return position + 1;
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