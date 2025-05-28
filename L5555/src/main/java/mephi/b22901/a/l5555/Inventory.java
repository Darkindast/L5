/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import java.util.Stack;
/**
 * Класс Inventory представляет инвентарь игрока, который содержит различные предметы.
 * Используются три стека для хранения зельев здоровья и крестов воскрешения.
 * Стек обеспечивает принцип "последним пришёл — первым вышел" (LIFO).
 * 
 * @author Arseniy
 */
public class Inventory {
    
    private Stack<BigHealthPotion>  bigHealthPotions;
    private  Stack<SmallHealthPotion>  smallHealthPotions;
    private Stack<RessurectionCross>  ressurectionCrosses;
   
    public Inventory(){
       bigHealthPotions = new Stack<>();
       smallHealthPotions = new Stack<>();
       ressurectionCrosses = new Stack<>();
    }
    
    public BigHealthPotion getBigHealthPotion(){
        BigHealthPotion bigHealthPotion = bigHealthPotions.pop();
        return bigHealthPotion;
    }
   
    public SmallHealthPotion getSmallHealthPotion(){
        SmallHealthPotion smallHealthPotion = smallHealthPotions.pop();
        return smallHealthPotion;
    }
   
    public RessurectionCross getRessurectionCross(){
        RessurectionCross ressurectionCross = ressurectionCrosses.pop();
        return ressurectionCross;
    }
   
    public int getBigHealthPotionCount() {
        return bigHealthPotions.size();
    }

    public int getSmallHealthPotionCount() {
        return smallHealthPotions.size();
    }

    public int getRessurectionCrossCount() {
        return ressurectionCrosses.size();
    }

    public void addBigHealthPotion(BigHealthPotion potion) {
        bigHealthPotions.push(potion);
    }

    public void addSmallHealthPotion(SmallHealthPotion potion) {
        smallHealthPotions.push(potion);
    }

    public void addRessurectionCross(RessurectionCross cross) {
        ressurectionCrosses.push(cross);
    }
    
    /**
     * Возвращает информацию о текущем состоянии инвентаря в виде массива строк.
     * @return массив из трёх строк с количеством каждого типа предметов
     */
    public String[] getInventoryInfo() {
        String[] result = new String[3];
        result[0] = "Большое зелье лечения: " + getBigHealthPotionCount();
        result[1] = "Малое зелье лечения: " + getSmallHealthPotionCount();
        result[2] = "Крест воскрешения: " + getRessurectionCrossCount();
        return result;
    }
}