/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Интерфейс Item представляет базовый контракт для всех предметов,
 * которые могут использоваться в игре (например, зелья или другие объекты).
 * 
 * Каждый предмет должен иметь имя и коэффициент лечения.
 */
public interface Item {
    String getName();
    
    /**
     * Возвращает коэффициент лечения (множитель для восстановления здоровья).
     * Например, 1.0 означает обычное лечение, больше — усиленное.
     * @return коэффициент лечения в виде числа с плавающей точкой
     */
    double getHealKF();
}