/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import mephi.b22901.a.l5555.InventoryDialog;
import mephi.b22901.a.l5555.ActionType;
import mephi.b22901.a.l5555.BigHealthPotion;
import mephi.b22901.a.l5555.Game;
import mephi.b22901.a.l5555.Player;
import mephi.b22901.a.l5555.ShaoKahn;
import mephi.b22901.a.l5555.Human;
import mephi.b22901.a.l5555.RessurectionCross;
import mephi.b22901.a.l5555.SmallHealthPotion;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
/**
 * Класс {@code BattleFrame} представляет собой окно пошагового боя между игроком (объектом {@link Human})
 * и списком врагов ({@link Player}). Отображает здоровье, урон, уровень и состояния игроков, 
 * а также предоставляет кнопки для действий игрока: атака, защита, дебафф и использование предметов.
 * 
 * <p>Класс также обрабатывает переход к следующему врагу после победы, получение опыта, предметов,
 * и обработку проигрыша с возможностью воскрешения.</p>
 *
 * <p>Ключевые функции:</p>
 * <ul>
 *     <li>Обработка логики боя (атака, защита, дебафф)</li>
 *     <li>Обновление интерфейса в реальном времени</li>
 *     <li>Отображение опыта, состояния, побед, уровней и получаемых предметов</li>
 *     <li>Переход между врагами в рамках одной локации</li>
 *     <li>Уровневый рост игрока</li>
 * </ul>
 * 
 * @author 
 */
public class BattleFrame extends JFrame {

    private JButton btnAttack;
    private JButton btnDefend;
    private JButton btnItems;
    private JButton btnDebuff;
    private JProgressBar playerHpBar, enemyHpBar;
    private JLabel lblPlayerDamage, lblPlayerLevel;
    private JLabel lblEnemyDamage, lblEnemyLevel;
    private JLabel playerScoreLabel, playerExpLabel;
    private JTextArea logArea;
    private JLabel turnLabel = new JLabel(), playerStunLabel, enemyStunLabel;
    private JLabel playerIconLabel, enemyIconLabel, humanNameLabel, enemyNameLabel;
    
    private final Human human;
    private Player enemy;
    private final Game game;
    private final int currentLocation;
    private final int totalLocations;
    private final List<Player> enemyList;
    private int currentEnemyIndex = 0;
    private JLabel lastMessageLabel;
    

    public BattleFrame(Human human, List<Player> enemyList, Game game, int currentLocation, int totalLocations) {
        super("Битва");
        this.human = human;
        human.setHealth(human.getMaxHealth());
        this.enemyList = enemyList;
        this.enemy = enemyList.get(0);
        this.game = game;
        this.currentLocation = currentLocation;
        this.totalLocations = totalLocations;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }
    
    public int getCurrentLocation(){
        return this.currentLocation;
    }
    
    public int getTotalLocations(){
        return this.totalLocations;
    }
    
    public Human getHuman(){
        return this.human;
    }
    
    public Game getGame(){
        return this.game;
    }
    
    /**
    * Инициализирует и размещает основные визуальные компоненты окна боя:
    * заголовок, панели игрока и врага, центральную панель с логом и индикаторами,
    * а также нижнюю панель с кнопками действий.
    */
    private void initializeComponents() {
    // Установка фона
    setBackground(Color.BLACK);
    
    // Шрифт в стиле Mortal Kombat (если не найден, используем Arial)
    Font mkFont;
    try {
        mkFont = Font.createFont(Font.TRUETYPE_FONT, 
                getClass().getResourceAsStream("/fonts/mk.ttf")).deriveFont(Font.BOLD, 24f);
    } catch (Exception e) {
        mkFont = new Font("Arial", Font.BOLD, 24);
    }

  
 

    // Центральная панель (для логов)
    JPanel centerPanel = createNewCenterPanel();
    add(centerPanel, BorderLayout.CENTER);

    // Панель кнопок
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    
    // Стилизация кнопок
    btnAttack = createMkButton("ATTACK");
    btnDefend = createMkButton("DEFEND");
    btnDebuff = createMkButton("DEBUFF");
    btnItems = createMkButton("ITEMS");
    
    btnDebuff.setEnabled(enemy.getDebuff() == 0 && human.getLevel() != 0);
    
    buttonPanel.add(btnAttack);
    buttonPanel.add(btnDefend);
    buttonPanel.add(btnDebuff);
    buttonPanel.add(btnItems);
    
    add(buttonPanel, BorderLayout.SOUTH);
}

// Вспомогательный метод для создания стилизованных кнопок
private JButton createMkButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(70, 0, 0));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    button.setPreferredSize(new Dimension(120, 40));
    
    // Эффекты при наведении
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(120, 0, 0));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(70, 0, 0));
        }
    });
    
    return button;
}
//  private JPanel createPlayerPanel(Player player, boolean isHuman) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setPreferredSize(new Dimension(150, 0));
//
//        JProgressBar hpBar = new JProgressBar(0, player.getMaxHealth());
//        hpBar.setValue(player.getHealth());
//        hpBar.setStringPainted(true);
//        
//        if(isHuman) playerHpBar = hpBar;
//        else enemyHpBar = hpBar;
//        hpBar.setForeground(Color.GREEN);
//        
//        if(isHuman) {
//            humanNameLabel = new JLabel("Игрок " + player.getName(), SwingConstants.CENTER);
//            panel.add(humanNameLabel);
//        } else {
//            enemyNameLabel = new JLabel("Враг " + player.getName(), SwingConstants.CENTER);
//            panel.add(enemyNameLabel);
//        }
//        panel.add(hpBar);
//        panel.add(Box.createVerticalStrut(6));
//
//        JLabel lblDamage = new JLabel("Урон: " + player.getDamage());
//        JLabel lblLevel = new JLabel("Уровень: " + player.getLevel());
//        if(isHuman) {
//            lblPlayerDamage = lblDamage;
//            lblPlayerLevel = lblLevel;
//        } else {
//            lblEnemyDamage = lblDamage;
//            lblEnemyLevel = lblLevel;
//        }
//        panel.add(lblDamage);
//        panel.add(lblLevel);
//        panel.add(Box.createVerticalStrut(10));
//        ImageIcon icon = null;
//        if(isHuman) {
//            icon = new ImageIcon(getClass().getResource(human.getIconSource()));
//            playerIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
//            panel.add(playerIconLabel);
//        } else {
//            icon = new ImageIcon(getClass().getResource(enemy.getIconSource()));
//            enemyIconLabel = new JLabel("Текст", icon, JLabel.CENTER);
//            panel.add(enemyIconLabel);
//        }
//        panel.add(Box.createVerticalGlue());
//        return panel;
//    }
    /**
    * Создает панель с информацией об игроке или враге.
    *
    * @param player Объект игрока или врага.
    * @param isHuman true, если панель создается для игрока; false — для врага.
    * @return JPanel с визуальным представлением игрока/врага.
    */
    private JPanel createNewCenterPanel() {
    JPanel center = new JPanel(new BorderLayout());
    center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    center.setBackground(Color.BLACK);

    // Шрифт в стиле Mortal Kombat (если не найден, используем Arial)
    Font mkFont;
    try {
        mkFont = Font.createFont(Font.TRUETYPE_FONT, 
                getClass().getResourceAsStream("/fonts/mk.ttf")).deriveFont(Font.BOLD, 24f);
    } catch (Exception e) {
        mkFont = new Font("Arial", Font.BOLD, 24);
    }

    // Верхняя панель с информацией о локации
    JLabel locationLabel = new JLabel("LOCATION " + currentLocation, SwingConstants.CENTER);
    locationLabel.setFont(mkFont.deriveFont(28f));
    locationLabel.setForeground(Color.RED);
    center.add(locationLabel, BorderLayout.NORTH);

    // Центральная панель с игроками
    JPanel playersPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    playersPanel.setBackground(Color.BLACK);

    // Панель игрока
    JPanel playerPanel = new JPanel();
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    playerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2), "PLAYER", 
            TitledBorder.CENTER, TitledBorder.TOP, mkFont, Color.CYAN));
    
    humanNameLabel = new JLabel(human.getName(), SwingConstants.CENTER);
    humanNameLabel.setFont(mkFont.deriveFont(20f));
    humanNameLabel.setForeground(Color.WHITE);
    playerPanel.add(humanNameLabel);
    
    playerIconLabel = new JLabel(new ImageIcon(getClass().getResource(human.getIconSource())));
    playerIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    playerPanel.add(playerIconLabel);
    
    playerHpBar = new JProgressBar(0, human.getMaxHealth());
    playerHpBar.setValue(human.getHealth());
    playerHpBar.setStringPainted(true);
    playerHpBar.setForeground(Color.GREEN);
    playerHpBar.setBackground(Color.DARK_GRAY);
    playerHpBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    playerPanel.add(playerHpBar);
    
    lblPlayerLevel = new JLabel("LEVEL: " + human.getLevel(), SwingConstants.CENTER);
    lblPlayerLevel.setFont(mkFont.deriveFont(16f));
    lblPlayerLevel.setForeground(Color.WHITE);
    
    lblPlayerDamage = new JLabel("DAMAGE: " + human.getDamage(), SwingConstants.CENTER);
    lblPlayerDamage.setFont(mkFont.deriveFont(16f));
    lblPlayerDamage.setForeground(Color.WHITE);
    
    playerPanel.add(lblPlayerLevel);
    playerPanel.add(lblPlayerDamage);
    
    playerStunLabel = new JLabel("STUNNED: " + human.isStunned(), SwingConstants.CENTER);
    playerStunLabel.setFont(mkFont.deriveFont(14f));
    playerStunLabel.setForeground(Color.YELLOW);
    playerPanel.add(playerStunLabel);

    // Панель врага
    JPanel enemyPanel = new JPanel();
    enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
    enemyPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.RED, 2), "ENEMY", 
            TitledBorder.CENTER, TitledBorder.TOP, mkFont, Color.RED));
    
    enemyNameLabel = new JLabel(enemy.getName(), SwingConstants.CENTER);
    enemyNameLabel.setFont(mkFont.deriveFont(20f));
    enemyNameLabel.setForeground(Color.WHITE);
    enemyPanel.add(enemyNameLabel);
    
    enemyIconLabel = new JLabel(new ImageIcon(getClass().getResource(enemy.getIconSource())));
    enemyIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    enemyPanel.add(enemyIconLabel);
    
    enemyHpBar = new JProgressBar(0, enemy.getMaxHealth());
    enemyHpBar.setValue(enemy.getHealth());
    enemyHpBar.setStringPainted(true);
    enemyHpBar.setForeground(Color.RED);
    enemyHpBar.setBackground(Color.DARK_GRAY);
    enemyHpBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    enemyPanel.add(enemyHpBar);
    
    lblEnemyLevel = new JLabel("LEVEL: " + enemy.getLevel(), SwingConstants.CENTER);
    lblEnemyLevel.setFont(mkFont.deriveFont(16f));
    lblEnemyLevel.setForeground(Color.WHITE);
    
    lblEnemyDamage = new JLabel("DAMAGE: " + enemy.getDamage(), SwingConstants.CENTER);
    lblEnemyDamage.setFont(mkFont.deriveFont(16f));
    lblEnemyDamage.setForeground(Color.WHITE);
    
    enemyPanel.add(lblEnemyLevel);
    enemyPanel.add(lblEnemyDamage);
    
    enemyStunLabel = new JLabel("STUNNED: " + enemy.isStunned(), SwingConstants.CENTER);
    enemyStunLabel.setFont(mkFont.deriveFont(14f));
    enemyStunLabel.setForeground(Color.YELLOW);
    enemyPanel.add(enemyStunLabel);

    playersPanel.add(playerPanel);
    playersPanel.add(enemyPanel);
    center.add(playersPanel, BorderLayout.CENTER);

    // Нижняя панель с логом и статистикой
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
    bottomPanel.setBackground(Color.BLACK);
    
    // Label для последнего сообщения
    lastMessageLabel = new JLabel("FIGHT STARTED!", SwingConstants.CENTER);
    lastMessageLabel.setFont(mkFont.deriveFont(20f));
    lastMessageLabel.setForeground(Color.YELLOW);
    lastMessageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    bottomPanel.add(lastMessageLabel);
    
    // Панель с очками и опытом
    JPanel statsPanel = new JPanel();
    statsPanel.setBackground(Color.BLACK);
    
    playerScoreLabel = new JLabel("SCORE: " + human.getPoints());
    playerScoreLabel.setFont(mkFont.deriveFont(16f));
    playerScoreLabel.setForeground(Color.WHITE);
    
    playerExpLabel = new JLabel("XP: " + human.getExperience() + "/" + human.getRequiredExperience());
    playerExpLabel.setFont(mkFont.deriveFont(16f));
    playerExpLabel.setForeground(Color.WHITE);
    
    statsPanel.add(playerScoreLabel);
    statsPanel.add(Box.createHorizontalStrut(30));
    statsPanel.add(playerExpLabel);
    
    bottomPanel.add(statsPanel);
    center.add(bottomPanel, BorderLayout.SOUTH);

    return center;
}
     public void addToLog(String message) {
        // Форматируем как HTML для поддержки переноса строк
        lastMessageLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>");
    }
    /**
    * Привязывает обработчики действий к кнопкам: атака, защита, ослабление, предметы.
    */
    private void setupActions() {
        btnAttack.addActionListener(this::onAttackClicked);
        btnDefend.addActionListener(this::onDefendClicked);
        btnDebuff.addActionListener(this::onDebuffClicked);
        btnItems.addActionListener(this::onItemsClicked);
    }
    
    /**
    * Обновляет все визуальные элементы, отражающие текущее состояние игроков:
    * здоровье, уровень, урон, статус оглушения и доступность кнопки "Ослабить".
    * Также изменяет цвет полосы здоровья в зависимости от текущего состояния.
    */
    void updateLabels() {
        playerHpBar.setValue(human.getHealth());
        enemyHpBar.setValue(enemy.getHealth());
        if(human.getHealth() < human.getMaxHealth() * 0.25){
            playerHpBar.setForeground(Color.RED);
        } else {
            playerHpBar.setForeground(Color.GREEN);
        }
        if(enemy.getHealth() < enemy.getMaxHealth() * 0.25){  
            enemyHpBar.setForeground(Color.RED);
        } else {
            enemyHpBar.setForeground(Color.GREEN);
        }
        if(enemy.getDebuff() != 0){
            enemyHpBar.setForeground(Color.BLUE);
        }
        if(human.getDebuff() != 0){
            playerHpBar.setForeground(Color.BLUE);
        }
        lblPlayerDamage.setText("Урон: " + human.getDamage());
        lblPlayerLevel.setText("Уровень: " + human.getLevel());
        lblEnemyDamage.setText("Урон: " + enemy.getDamage());
        lblEnemyLevel.setText("Уровень: " + enemy.getLevel());
        
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        
        btnDebuff.setEnabled(
            human.getDebuff() == 0 &&
            enemy.getDebuff() == 0 &&
            human.getLevel() != 0
        );
    }
    
    /**
    * Обработчик нажатия кнопки "Атаковать".
    * Выполняет атаку игрока, обновляет интерфейс, обрабатывает эффекты дебаффов,
    * проверяет условия победы и поражения, а также переключает ход.
    *
    * @param e Событие нажатия кнопки.
    */
    public void onAttackClicked(ActionEvent e) {
        
        updateLabels();
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.ATTACK);
        addToLog(battleLog); // Заменяем logArea.append()
        updateLabels();
        checkWinCondition();
        checkLoseCondition();

        enemy.reduceDebuffDuration();
        if(enemy.getDebuff() == 0 && human.getDebuff() == 0 ){
            enemy.resetDebuff(human);
        }

        human.reduceDebuffDuration();
        if(human.getDebuff() == 0 && enemy.getDebuff() == 0 ){
            human.resetDebuff(enemy);
        }
        
        updateLabels();
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
   
    /**
    * Обработчик нажатия кнопки "Защититься".
    * Выполняет действие защиты, обновляет интерфейс, учитывает пропуск снижения дебаффа
    * при определённых условиях, проверяет условия победы и поражения.
    *
    * @param e Событие нажатия кнопки.
    */
    public void onDefendClicked(ActionEvent e) {
       updateLabels();    
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.DEFEND);
        addToLog(battleLog);
        updateLabels();
        checkWinCondition();
        checkLoseCondition();

        enemy.reduceDebuffDuration();
        if(enemy.getDebuff() == 0 && human.getDebuff() == 0 ){
            enemy.resetDebuff(human);
        }

        if (human.isSkipNextDebuffTurn()) {
            human.setSkipNextDebuffTurn(false);
        } else {
            human.reduceDebuffDuration();
        }
        if(human.getDebuff() == 0 && enemy.getDebuff() == 0 ){
            human.resetDebuff(enemy);
        }
        
        updateLabels();   
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
    
    /**
    * Обработчик нажатия кнопки "Ослабить".
    * Выполняет дебафф противника, обновляет интерфейс и проверяет условия окончания боя.
    *
    * @param e Событие нажатия кнопки.
    */
    private void onDebuffClicked(ActionEvent e) {
        updateLabels();
        String battleLog = game.fight.performPlayerAction(human, enemy, ActionType.DEBUFF);
        addToLog(battleLog);

        updateLabels();
        checkWinCondition();
        checkLoseCondition();
        
        turnLabel.setText("Ход игрока: " + game.fight.getIsPlayerTurn());
    }
    
    /**
    * Обработчик нажатия кнопки "Предметы".
    * Открывает диалоговое окно инвентаря и обновляет интерфейс после возможного применения предметов.
    *
    * @param e Событие нажатия кнопки.
    */
    public void onItemsClicked(ActionEvent e) {
        InventoryDialog inventoryDialog = new InventoryDialog(this);
        inventoryDialog.setVisible(true);
        updateLabels();
    }
    
    /**
    * Проверяет условие победы игрока: если здоровье врага <= 0,
    * начисляет очки, опыт, дропает предметы, запускает следующего врага или завершает игру.
    */
    private void checkWinCondition() {
        if (enemy.getHealth() <= 0) {
            human.addWin();
            human.addPoints(enemy.getReceivedPoints());
            currentEnemyIndex++;
            int defeatedNumber = currentEnemyIndex;
            int totalEnemies = enemyList.size();
            JOptionPane.showMessageDialog(this, 
                "Побежден враг " + defeatedNumber + " из " + totalEnemies + "!",
                "Победа!", 
                JOptionPane.INFORMATION_MESSAGE);
            
            human.gainExperience(enemy.returnExperienceForWin());
            checkLevelUpdate();
            playerExpLabel.setText("Опыт: " + human.getExperience() + "/" + human.getRequiredExperience());
            processItemDrop();
//            logArea.setText("");
            if (currentEnemyIndex < enemyList.size()) {
                enemy = enemyList.get(currentEnemyIndex);
                resetBattle();
                addToLog("Победа над " + enemy.getName() + "! Следующий враг: " + 
                    (currentEnemyIndex < enemyList.size() ? enemyList.get(currentEnemyIndex).getName() : "нет"));
            } else {
                WinDialog winDialog = new WinDialog(this);
                winDialog.setVisible(true);
                setVisible(false);
            }
        }
    }
    
    /**
    * Обрабатывает выпадение предметов после победы.
    * Шанс выпадения зависит от типа врага. Возможные предметы:
    * малое зелье лечения, большое зелье лечения и крест возрождения.
    */
    private void processItemDrop() {
        double smallPotionDropP = Math.random();
        double bigPotionDropP = Math.random();
        double ressurectionCrossDropP = Math.random(); 
        double probabilityMultiplier = 1;
        if (enemy instanceof ShaoKahn){
            probabilityMultiplier = 1.5;
        }
        if (smallPotionDropP < 0.25 * probabilityMultiplier) {
            human.getInventory().addSmallHealthPotion(new SmallHealthPotion());
            JOptionPane.showMessageDialog(this, "Вам выпало: малое зелье лечения!");
        } else if (bigPotionDropP < 0.15 * probabilityMultiplier) {
            human.getInventory().addBigHealthPotion(new BigHealthPotion());
            JOptionPane.showMessageDialog(this, "Вам выпало: большое зелье лечения!");
        } else if (ressurectionCrossDropP < 0.05 * probabilityMultiplier) {
            human.getInventory().addRessurectionCross(new RessurectionCross());
            JOptionPane.showMessageDialog(this, "Вам выпал: крест возрождения!");
        }
    }

    /**
    * Проверяет, достиг ли игрок нового уровня.
    * Если достиг — повышает уровень, предлагает выбор улучшения и обновляет характеристики врагов.
    */
    public void checkLevelUpdate(){
        if (human.getExperience() >= human.getRequiredExperience()){
            human.levelUp();
            showLevelUpDialog();
            human.setRequiredExperiance();
            for(Player enemy: enemyList){
                enemy.updateCharacteristicsBasedOnLevel(human.getLevel());
            }
        }
    }
    
    /**
    * Отображает диалог повышения уровня, предлагая игроку выбор между увеличением урона и здоровья.
    * Применяет выбранное улучшение и обновляет интерфейс.
    */
    private void showLevelUpDialog() {
        Object[] options = {"Увеличить урон (+20%)", "Увеличить здоровье (+25%)"};
        int choice = JOptionPane.showOptionDialog(null,
                "Вы достигли уровня " + human.getLevel() + "! Выберите улучшение:",
                "Повышение уровня",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            human.setMaxDamage((int) (human.getMaxDamage() + human.getMaxDamage()*0.2));
        } else if (choice == 1) {
            human.setMaxHealth((int) (human.getMaxHealth() + human.getMaxHealth()*0.25));
        }
        updateLabels();
    }

    /**
    * Проверяет условие поражения игрока.
    * Если здоровье игрока <= 0, пытается использовать крест воскрешения.
    * Если не удалось воскреснуть — сбрасывает битву.
    */
    private void checkLoseCondition() {
        if (human.getHealth() <= 0) {
            boolean resurrected = tryRessurection();
            if (resurrected){
                updateLabels();
            } else {
                JOptionPane.showMessageDialog(this, "Вы проиграли!");
                resetBattle();
            }
        }
    }
    
    /**
    * Пытается воскресить игрока, если в инвентаре есть крест воскрешения.
    * Восстанавливает 5% от максимального здоровья. Показывает сообщение о воскрешении.
    *
    * @return true, если воскрешение произошло, иначе false.
    */
    private boolean tryRessurection() {
        if (human.getInventory().getRessurectionCrossCount() > 0) {
            RessurectionCross ressurectionCross = human.getInventory().getRessurectionCross();
            int restored = (int) Math.ceil(human.getMaxHealth() * ressurectionCross.getHealKF());
            if (restored < 1) restored = 1;
            human.setHealth(restored);
            JOptionPane.showMessageDialog(this, 
                "Крест возрождения спасает вас! Ваше здоровье восстановлено на 5% (" + restored + " HP).",
                "Воскрешение", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    
    /**
    * Сбрасывает состояние боя: восстанавливает здоровье, урон, статус,
    * иконки, полоски здоровья, и очищает лог. Применяется при начале новой битвы
    * или после воскрешения/поражения.
    */
    private void resetBattle() {
        human.setHealth(human.getMaxHealth());
        enemy.setHealth(enemy.getMaxHealth());

        human.resetStatus();
        enemy.resetStatus();

        human.resetDebuff(enemy);
        enemy.resetDebuff(human);
        
        human.setDamage(human.getMaxDamage());
        enemy.setDamage(enemy.getMaxDamage());
        
        playerIconLabel.setIcon(new ImageIcon(
            getClass().getResource(human.getIconSource()))
        );
        enemyIconLabel.setIcon(new ImageIcon(
            getClass().getResource(enemy.getIconSource()))
        );
        
        playerScoreLabel.setText("Очки: " + human.getPoints());
        
        enemyNameLabel.setText("Враг " + enemy.getName());
        
        playerHpBar.setMaximum(human.getMaxHealth());
        enemyHpBar.setMaximum(enemy.getMaxHealth());

        updateLabels();
        addToLog("Новый бой с " + enemy.getName()); 
        
        playerStunLabel.setText("Игрок оглушен: " + human.isStunned());
        enemyStunLabel.setText("Враг оглушен: "+ enemy.isStunned());
        
        playerHpBar.setForeground(Color.GREEN);
        enemyHpBar.setForeground(Color.GREEN);
    }
}