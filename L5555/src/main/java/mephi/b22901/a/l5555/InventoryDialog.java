/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * Диалоговое окно для отображения и использования предметов из инвентаря игрока.
 * Наследуется от JDialog и блокирует родительское окно BattleFrame до закрытия.
 * 
 * Позволяет выбрать предмет из списка и применить его действие.
 */
public class InventoryDialog extends JDialog {

    private JList<String> itemsList;
    private JButton btnUseItem;
    private GameFrame parentFrame; 

    public InventoryDialog(GameFrame parent) {
        super(parent, "Мешок предметов", true);
        this.parentFrame = parent;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }

    /**
     * Инициализация компонентов окна:
     * - Список предметов
     * - Кнопка "Использовать"
     * - Панель для кнопки
     */
    private void initializeComponents() {
        itemsList = new JList<>(parentFrame.getHuman().getInventory().getInventoryInfo());
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setLayoutOrientation(JList.VERTICAL);
        itemsList.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(itemsList);
        btnUseItem = new JButton("Использовать");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnUseItem);
        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActions() {
        btnUseItem.addActionListener(this::onUseItemClicked);
    }
    
    /**
     * Обработчик нажатия кнопки "Использовать".
     * Пытается применить выбранный предмет из инвентаря.
     * 
     * @param e объект события нажатия кнопки
     */
    public void onUseItemClicked(ActionEvent e) {
        String selectedItem = itemsList.getSelectedValue();

        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Выберите предмет для использования.", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Inventory inventory = parentFrame.getHuman().getInventory();
        boolean success = false;
        String usedItemName = null;
        String itemType = selectedItem.split(":")[0].trim();
        switch (itemType) {
            case "Большое зелье лечения":
                if (inventory.getBigHealthPotionCount() > 0) {
                    BigHealthPotion bigPotion = inventory.getBigHealthPotion();
                    parentFrame.getHuman().heal(bigPotion);
                    usedItemName = "Большое зелье лечения";
                    parentFrame.updateLabels();
                    success = true;
                }
                break;
            case "Малое зелье лечения":
                if (inventory.getSmallHealthPotionCount() > 0) {
                    SmallHealthPotion smallPotion = inventory.getSmallHealthPotion();
                    parentFrame.getHuman().heal(smallPotion);
                    usedItemName = "Малое зелье лечения";
                    parentFrame.updateLabels();
                    success = true;
                }
                break;
            case "Крест воскрешения":
                if (inventory.getRessurectionCrossCount() > 0) {
                    JOptionPane.showMessageDialog(this, "Вы не можете использовать этот предмет");
                    success = false;
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Неизвестный предмет: " + selectedItem, "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
        }
        if (success) {
            JOptionPane.showMessageDialog(this, "Вы использовали предмет: " + usedItemName);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Закончились предметы или нельзя использовать: " + itemType, "Ошибка", JOptionPane.WARNING_MESSAGE);
            dispose();
        }
    }
}