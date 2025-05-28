/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * Класс Game отвечает за создание и управление врагами и игроком,
 * а также за инициализацию боя.
 * 
 * @author Арсения
 */
public class Game {
    
    /** Массив шаблонов врагов разных типов для текущей локации */
    private static Player enemies[] = new Player[4];
    /** Объект боя, управляющий логикой пошагового сражения */
    public Fight fight = new Fight();
    
    /**
     * Генерирует список врагов для текущей локации в зависимости от уровня игрока.
     * Для каждого шаблона врага обновляет характеристики под уровень игрока.
     * Затем создаёт список врагов, включая нескольких случайных и одного босса.
     * 
     * @param playerLevel уровень игрока
     * @return список врагов для локации
     */
    public List<Player> generateEnemiesForLocation(int playerLevel) {
        setEnemies();
        for(Player enemy: enemies){
            enemy.updateCharacteristicsBasedOnLevel(playerLevel);
        }
        List<Player> enemies = new ArrayList<>();
        int count = playerLevel + 3 +  new Random().nextInt(2);       
        for (int i = 0; i < count - 1; i++) {
            enemies.add(NewEnemy());
        }
        Player boss = NewBoss();
        enemies.add(boss);
        return enemies;
    }
    
    /**
     * Создаёт нового босса с помощью фабрики врагов.
     * 
     * @return объект босса
     */
    public Player NewBoss(){
        Player boss = makeBoss();
        return boss;
    }
    
    /**
     * Создаёт нового врага случайного типа.
     * 
     * @return объект врага
     */
    public Player NewEnemy() {
        Player enemy = ChooseEnemy();
        return enemy;
    }
    
    /**
     * Инициализирует массив шаблонов врагов всех типов.
     * Вызывается перед генерацией врагов для локации.
     */
    public static void setEnemies() {
        enemies[0] = EnemyFactory.createEnemy(EnemyType.TANK);      
        enemies[1] = EnemyFactory.createEnemy(EnemyType.MAGICIAN);      
        enemies[2] = EnemyFactory.createEnemy(EnemyType.FIGHTER);      
        enemies[3] = EnemyFactory.createEnemy(EnemyType.SOLDIER);   
    }
    
    /**
     * Случайным образом выбирает одного из шаблонных врагов из массива enemies.
     * 
     * @return объект врага выбранного типа
     */
    public Player ChooseEnemy() {
        int randomEnemyIndex = (int) (Math.random() * 4);
        Player enemy = null;
        switch (randomEnemyIndex) {
            case 0:
                enemy = enemies[0];
                break;
            case 1:
                enemy = enemies[1];
                break;
            case 2:
                enemy = enemies[2];
                break;
            case 3:
                enemy = enemies[3];
                break;
        }
        return enemy;
    }
    
    /**
     * Создаёт босса с помощью фабрики врагов.
     * 
     * @return объект босса
     */
    public Player makeBoss(){
        return EnemyFactory.createEnemy(EnemyType.BOSS);
    }
    
    /**
     * Создаёт нового игрока (человека) с начальными параметрами.
     * 
     * @return объект игрока
     */
    public Human NewHuman(){
        Human human = new Human (0, 100, 50);
        return human;
    }  
}