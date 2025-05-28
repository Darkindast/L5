/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

/**
 * Класс {@code BigHealthPotion} представляет большой лечебный предмет,
 * реализующий интерфейс {@link Item}. При использовании восстанавливает
 * 50% от максимального здоровья персонажа.
 * <p>
 * Используется в бою или инвентаре игрока для восстановления здоровья.
 * </p>
 * 
 * @author Arseniy
 */
public class BigHealthPotion implements Item {

    /**
     * Возвращает название предмета.
     *
     * @return строка "Большое зелье лечения"
     */
    @Override
    public String getName() {
        return "Большое зелье лечения";
    }

    /**
     * Возвращает коэффициент лечения, применяемый к максимальному здоровью
     * персонажа при использовании зелья.
     *
     * @return 0.5, что соответствует восстановлению 50% здоровья
     */
    @Override
    public double getHealKF() {
        return 0.5;
    }  
}