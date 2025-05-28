/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Класс {@code EnemyFactory} реализует фабричный метод для создания врагов
 * различных типов на основе перечисления {@link EnemyType}.
 * <p>
 * Используется для инкапсуляции логики создания объектов врагов с начальными параметрами.
 * </p>
 * 
 * @author Арсений
 */
public class EnemyFactory {
    /**
     * Создаёт объект врага соответствующего типа с предопределёнными характеристиками.
     *
     * @param type тип врага, указанный в {@link EnemyType}
     * @return экземпляр {@link Player}, представляющий врага
     * @throws IllegalArgumentException если передан неизвестный тип врага
     */
    public static Player createEnemy(EnemyType type) {
        switch (type) {
            case TANK:
                return new Baraka(0, 100, 12);
            case MAGICIAN:
                return new SubZero(0, 85, 16);
            case FIGHTER:
                return new LiuKang(0, 70, 20);
            case SOLDIER:
                return new SonyaBlade(0, 80, 16);
            case BOSS:
                return new ShaoKahn(3, 145, 30);
            default:
                throw new IllegalArgumentException("Такого типа соперника нет: " + type);
        }
    }
}