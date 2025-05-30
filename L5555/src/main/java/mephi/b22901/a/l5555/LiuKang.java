/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Класс персонажа Liu Kang, наследник Player.
 * Определяет уникальные характеристики и поведение Liu Kang.
 */
public class LiuKang extends Player {
    
    /**
     * Конструктор, инициализирующий уровень, здоровье и урон персонажа.
     * 
     * @param level уровень персонажа
     * @param health здоровье персонажа
     * @param damage урон персонажа
     */
    public LiuKang(int level, int health, int damage) {
        super(level, health, damage);
    }
    
    /**
     * Возвращает имя персонажа.
     * @return имя персонажа "Liu Kang"
     */
    @Override
    public String getName() {
        return "Liu Kang";
    }
    
    /**
     * Возвращает путь к иконке персонажа.
     * @return строка с путем к изображению иконки
     */
    @Override
    public String getIconSource() {
        return "/Liu_Kang.gif";
    }

    /**
     * Возвращает количество очков, получаемых за победу над этим персонажем.
     * Зависит от уровня персонажа.
     * @return очки за победу
     */
    @Override
    public int getReceivedPoints() {
        return 120 + level * 20;
    }
    
    /**
     * Возвращает количество опыта, которое получает игрок за победу над этим персонажем.
     * @return количество опыта
     */
    @Override
    public int returnExperienceForWin() {
        return 20;
    }
    
    /**
     * Обновляет характеристики персонажа (здоровье, урон) в зависимости от уровня игрока.
     * @param playerLevel уровень игрока
     */
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 70 + playerLevel * 15;
        int updatedDamage = 20 + playerLevel * 5;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}