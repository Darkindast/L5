/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import javax.swing.*;
import java.awt.*;

/**
 * Диалоговое окно для выбора количества локаций.
 * Пользователь вводит число от 1 до 10.
 * Если введено корректное число — диалог закрывается и результат можно получить через геттеры.
 * Наследуется от JDialog, является модальным, блокирует родительское окно.
 */
public class LocationDialog extends JDialog {
    private int locations = -1;
    private boolean confirmed = false;

    private JLabel jLabelCountLocation;
    private JTextField jTextFieldCountLocation;
    private JButton jButtonChoseLocation;
    private JPanel jPanelChoseLocation;

    /**
     * Конструктор диалога.
     * @param parent родительское окно (обычно JFrame)
     */
    public LocationDialog(JFrame parent) {
        super(parent, "Выберите количество локаций", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        initComponents();
        layoutComponents();
        registerEvents();
    }

    private void initComponents() {
        // Панель
        jPanelChoseLocation = new JPanel();
        jPanelChoseLocation.setBackground(Color.BLACK);

        // Метка
        jLabelCountLocation = new JLabel("Выберите количество локаций");
        jLabelCountLocation.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        jLabelCountLocation.setForeground(new Color(255, 165, 0));
        jLabelCountLocation.setHorizontalAlignment(SwingConstants.CENTER);

        // Поле ввода
        jTextFieldCountLocation = new JTextField();
        jTextFieldCountLocation.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        jTextFieldCountLocation.setHorizontalAlignment(JTextField.CENTER);
        jTextFieldCountLocation.setPreferredSize(new Dimension(100, 30));

        // Кнопка
        jButtonChoseLocation = new JButton("Начать");
        jButtonChoseLocation.setBackground(new Color(255, 165, 0));
        jButtonChoseLocation.setForeground(new Color(51, 51, 51));
        jButtonChoseLocation.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        jButtonChoseLocation.setPreferredSize(new Dimension(150, 40));
    }

    private void layoutComponents() {
        GroupLayout layout = new GroupLayout(jPanelChoseLocation);
        jPanelChoseLocation.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(jLabelCountLocation, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addComponent(jTextFieldCountLocation, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonChoseLocation, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(30)
                .addComponent(jLabelCountLocation)
                .addGap(20)
                .addComponent(jTextFieldCountLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(jButtonChoseLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
        );

        setContentPane(jPanelChoseLocation);
    }

    private void registerEvents() {
        jButtonChoseLocation.addActionListener(e -> {
            try {
                int value = Integer.parseInt(jTextFieldCountLocation.getText().trim());
                if (value >= 1 && value <= 10) {
                    locations = value;
                    confirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Введите число от 1 до 10.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Введите корректное число.");
            }
        });
    }

    public int getLocations() {
        return locations;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
