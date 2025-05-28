/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import mephi.b22901.a.l5555.ActionType;
import java.util.ArrayList;
import java.util.List;
/**
 * Абстрактный класс, представляющий базового игрока (персонажа или врага) в игре.
 * Содержит общие характеристики и поведение, наследуется конкретными классами персонажей.
 * 
 * @author Арсений
 */
public abstract class Player {
    
    protected int level;
    protected int health;
    protected int maxHealth;
    protected int maxDamage;
    protected int damage;
    protected int attack;
    protected int winAmount;
    protected int debuffTurnDuration = 0;
    private boolean skipNextDebuffTurn = false;
    private boolean stunned = false;
    ArrayList<ActionType> playerActionsHistory = new ArrayList<>();
    
    /**
     * Конструктор, инициализирующий базовые параметры игрока.
     * 
     * @param level уровень игрока
     * @param health начальное здоровье и максимальное здоровье
     * @param damage начальный урон и максимальный урон
     */
    public Player(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.maxDamage = damage;
        this.winAmount = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void levelUp() {
        this.level++;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * Восстанавливает здоровье игрока с помощью предмета (Item).
     * Лечит на коэффициент от максимального здоровья, заданный предметом.
     * При этом здоровье не превышает максимальное.
     * 
     * @param item предмет, с помощью которого игрок лечится
     */
    public void heal(Item item){
        double hu = item.getHealKF();
        this.health = (int) (health + maxHealth * hu);
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    public void setLevel(){
        this.level++;
    }
    
    /**
     * Выполняет атаку противника.
     * Записывает действие в историю.
     * 
     * @return урон для нанесения противнику
     */
    public int attackEnemy(){
        playerActionsHistory.add(ActionType.ATTACK);
        return this.damage;
    }
    
    
    /**
     * Защищается от атаки противника.
     * Записывает действие в историю.
     */
    public void defendFromEnemy(){
        playerActionsHistory.add(ActionType.DEFEND);
    }
    
    /**
     * Возвращает количество атак, совершенных игроком.
     * 
     * @return количество атак
     */
    public int getAttackCount() {
        int attackCount = 0;
        for (ActionType action : playerActionsHistory) {
            if (action == ActionType.ATTACK) {
                attackCount++;
            }
        }
        return attackCount;
    }
    
    /**
     * Возвращает количество защитных действий игрока.
     * 
     * @return количество защит
     */
    public int getDefendCount() {
        int defendCount = 0;
        for (ActionType action : playerActionsHistory) {
            if (action == ActionType.DEFEND) {
                defendCount++;
            }
        }
        return defendCount;
    }  

    public void setStunned(boolean val) {
        this.stunned = val;
    }

    public boolean isStunned() {
        return this.stunned;
    }
    
    public void resetStatus() {
        this.stunned = false;
    }
    
    public boolean isSkipNextDebuffTurn() {
        return skipNextDebuffTurn;
    }

    public void setSkipNextDebuffTurn(boolean skipNextDebuffTurn) {
        this.skipNextDebuffTurn = skipNextDebuffTurn;
    }
    
    /**
     * Накладывает дебафф на игрока от источника (другого игрока).
     * Уменьшает урон игрока вдвое, увеличивает урон источника на 25%.
     * Задает длительность дебаффа в зависимости от уровня источника.
     * Игрок пропускает следующий ход.
     * 
     * @param source игрок, который накладывает дебафф
     */
    public void setDebuff(Player source) {
        this.debuffTurnDuration += source.getLevel();
        this.setDamage((int)(this.getDamage() * 0.5));
        source.setDamage((int)(source.getMaxDamage() * 1.25));
        this.skipNextDebuffTurn = true;
    }

    public void increaseDuration(){
        this.debuffTurnDuration++;
    }
    
    /**
     * Сбрасывает дебафф и восстанавливает исходный урон для обоих игроков.
     * 
     * @param player игрок, который наложил дебафф
     */
    public void resetDebuff(Player player){
        this.setDamage(this.getMaxDamage());
        player.setDamage(player.getMaxDamage());
        this.debuffTurnDuration = 0;
        this.skipNextDebuffTurn = false;
    }
    
    public int getDebuff(){
        return this.debuffTurnDuration;
    }
    
    /**
     * Уменьшает длительность дебаффа на 1 ход, если дебафф активен.
     */
    public void reduceDebuffDuration(){
        if (debuffTurnDuration != 0){
            debuffTurnDuration--;
        }
    }
    
    public List<ActionType> getPlayerActionsHistory() {
        return this.playerActionsHistory;
    }
    
    public void addWin(){
        this.winAmount++;
    }
    
    public int getWinAmount(){
        return this.winAmount;
    }
    
    public abstract String getName();
    
    public abstract String getIconSource();
    
    public abstract int getReceivedPoints();
    
    public abstract int returnExperienceForWin();

    public abstract void updateCharacteristicsBasedOnLevel(int playerLevel);
    
}