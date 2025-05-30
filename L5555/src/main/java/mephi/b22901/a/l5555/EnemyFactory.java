
package mephi.b22901.a.l5555;
/**
 * Класс {@code EnemyFactory} реализует фабричный метод для создания врагов
 * различных типов на основе перечисления {@link EnemyType}.
 * <p>
 * Используется для инкапсуляции логики создания объектов врагов с начальными параметрами.
 * </p>
 */
public class EnemyFactory {
    /**
     * Создаёт объект врага соответствующего типа с предопределёнными характеристиками.
     *
     * @param type тип врага, указанный в {@link EnemyType}
     * @return экземпляр {@link Player}, представляющий врага
     * @throws IllegalArgumentException если передан неизвестный тип врага
     */
    public static Player createEnemy(int type) {
        switch (type) {
            case 1:
                return new Baraka(0, 100, 12);
            case 2:
                return new SubZero(0, 85, 16);
            case 3:
                return new LiuKang(0, 70, 20);
            case 4:
                return new SonyaBlade(0, 80, 16);
            case 5:
                return new ShaoKahn(3, 145, 30);
            default:
                throw new IllegalArgumentException("Такого типа соперника нет: " + type);
        }
    }
}