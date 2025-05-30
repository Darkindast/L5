/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Класс, представляющий персонажа Sonya Blade.
 * Наследуется от абстрактного класса Player.
 * Реализует уникальные характеристики и методы персонажа.
 */
public class SonyaBlade extends Player{
    /**
     * Конструктор класса SonyaBlade.
     * Инициализирует уровень, здоровье и урон персонажа.
     * 
     * @param level  уровень персонажа
     * @param health  начальное здоровье персонажа
     * @param damage  базовый урон персонажа
     */
    public SonyaBlade (int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Sonya Blade";
    }
    
    @Override
    public String getIconSource(){
        return "/sonya blade.gif";
    }

    @Override
    public int getReceivedPoints() {
        return 120 + level*10;
    }
    
    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    /**
     * Обновляет характеристики персонажа на основе уровня игрока.
     * Устанавливает здоровье и урон в зависимости от переданного уровня.
     * 
     * @param playerLevel текущий уровень игрока
     */
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 80 + (playerLevel) * 15;
        int updatedDamage = 16 + (playerLevel) * 5;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}