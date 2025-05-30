
package mephi.b22901.a.l5555;
/**
 * Класс, представляющий предмет "Крест воскрешения".
 * Реализует интерфейс Item.
 * Воскрешение восстанавливает небольшой процент здоровья.
 */
public class ResurrectionCross implements Item{

    @Override
    public String getName() {
        return "Крест воскрешения";
    }
    
    /**
     * Возвращает коэффициент лечения.
     * В данном случае восстанавливает 5% от максимального здоровья.
     * 
     * @return коэффициент лечения (0.05)
     */
    @Override
    public double getHealKF() {
        return 0.05;
    }    
}