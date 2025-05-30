
package mephi.b22901.a.l5555;
/**
 * Класс, представляющий малое зелье лечения.
 * Восстанавливает 25% от максимального здоровья.
 */
public class SmallHealthPotion implements Item{

    @Override
    public String getName() {
        return "Малое зелье лечения";
    }

    @Override
    public double getHealKF() {
        return 0.25;
    }
}