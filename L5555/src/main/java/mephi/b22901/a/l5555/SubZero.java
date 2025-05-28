/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Класс, представляющий персонажа Sub-Zero.
 * Наследуется от абстрактного класса Player.
 * Определяет уникальные характеристики и методы для Sub-Zero.
 * 
 * @author Арсений
 */
public class SubZero extends Player{
    /**
     * Конструктор класса SubZero.
     * Инициализирует уровень, здоровье и урон персонажа.
     * 
     * @param level  уровень персонажа
     * @param health начальное здоровье персонажа
     * @param damage базовый урон персонажа
     */
    public SubZero(int level, int health, int damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Sub-Zero";
    }
    
    @Override
    public String getIconSource(){
        return "/subzero.gif";
    }

    @Override
    public int getReceivedPoints() {
        return 100 + level*50;
    }

    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    /**
     * Обновляет характеристики персонажа Sub-Zero в зависимости от уровня игрока.
     * Здоровье и урон увеличиваются с повышением уровня.
     * 
     * @param playerLevel текущий уровень игрока
     */
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 85 + (playerLevel) * 15;
        int updatedDamage = 16 + (playerLevel) * 10;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}