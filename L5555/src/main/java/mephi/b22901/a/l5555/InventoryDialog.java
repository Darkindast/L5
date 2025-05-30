
package mephi.b22901.a.l5555;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Диалоговое окно инвентаря, стилизованное под Mortal Kombat.
 * Позволяет игроку просматривать и использовать предметы из инвентаря.
 */
public class InventoryDialog extends JDialog {

    private JList<String> itemsList;
    private JButton btnUseItem;
    private GameFrame parentFrame; 

    /**
     * Создает новое диалоговое окно инвентаря.
     *
     * @param parent родительское окно (главное окно игры)
     */
    public InventoryDialog(GameFrame parent) {
        super(parent, "INVENTORY", true);
        this.parentFrame = parent;
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        // Настройка стиля Mortal Kombat
        getContentPane().setBackground(Color.BLACK);
        setTitle("INVENTORY");
        
        initializeComponents();
        setupActions();
    }

    /**
     * Инициализация компонентов окна в стиле Mortal Kombat:
     * - Список предметов с черно-красным дизайном
     * - Кнопка "USE ITEM" в стиле MK
     * - Заголовок "SELECT ITEM"
     */
    private void initializeComponents() {
        // Настройка списка предметов
        itemsList = new JList<>(parentFrame.getHuman().getInventory().getInventoryInfo());
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setLayoutOrientation(JList.VERTICAL);
        itemsList.setVisibleRowCount(-1);
        
        // Стилизация списка под Mortal Kombat
        itemsList.setBackground(new Color(30, 30, 30));
        itemsList.setForeground(Color.RED);
        itemsList.setFont(new Font("Arial", Font.BOLD, 14));
        itemsList.setSelectionBackground(Color.RED);
        itemsList.setSelectionForeground(Color.BLACK);
        
        JScrollPane listScroller = new JScrollPane(itemsList);
        listScroller.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        listScroller.getViewport().setBackground(new Color(30, 30, 30));
        
        // Стилизация кнопки использования
        btnUseItem = new JButton("USE ITEM");
        btnUseItem.setFont(new Font("Arial", Font.BOLD, 14));
        btnUseItem.setForeground(Color.BLACK);
        btnUseItem.setBackground(Color.RED);
        btnUseItem.setFocusPainted(false);
        btnUseItem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(btnUseItem);
        
        // Заголовок в стиле MK
        JLabel titleLabel = new JLabel("SELECT ITEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
        
        add(titleLabel, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Настройка обработчиков событий.
     */
    private void setupActions() {
        btnUseItem.addActionListener(this::onUseItemClicked);
    }
    
    /**
     * Обработчик нажатия кнопки "USE ITEM".
     * Пытается применить выбранный предмет из инвентаря.
     * 
     * @param e объект события нажатия кнопки
     */
    public void onUseItemClicked(ActionEvent e) {
        String selectedItem = itemsList.getSelectedValue();

        if (selectedItem == null) {
            showMkStyleMessage("SELECT ITEM FIRST", "ERROR", JOptionPane.WARNING_MESSAGE);
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
                    showMkStyleMessage("YOU CANNOT USE THIS ITEM", "WARNING", JOptionPane.WARNING_MESSAGE);
                    success = false;
                }
                break;
            default:
                showMkStyleMessage("UNKNOWN ITEM: " + selectedItem, "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        if (success) {
            showMkStyleMessage("ITEM USED: " + usedItemName, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showMkStyleMessage("NO ITEMS LEFT OR CANNOT USE: " + itemType, "ERROR", JOptionPane.WARNING_MESSAGE);
            dispose();
        }
    }
    
    /**
     * Показывает стилизованное сообщение в духе Mortal Kombat.
     *
     * @param message текст сообщения
     * @param title заголовок сообщения
     * @param messageType тип сообщения (JOptionPane.ERROR_MESSAGE и т.д.)
     */
    private void showMkStyleMessage(String message, String title, int messageType) {
        // Временная установка стиля MK для сообщений
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.RED);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 12));
        
        JOptionPane.showMessageDialog(this, 
            "<html><center><font color='red'>" + message + "</font></center></html>", 
            title, 
            messageType);
        
        // Восстановление стандартных настроек
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
    }
}