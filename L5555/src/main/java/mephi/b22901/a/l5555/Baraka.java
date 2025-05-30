
package mephi.b22901.a.l5555;

/**
 * Класс {@code Baraka} представляет игрового персонажа по имени Барака,
 * наследующего функциональность от класса {@link Player}.
 */
public class Baraka extends Player {

    /**
     * Создает нового персонажа Baraka с заданными характеристиками.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage начальный урон
     */
    public Baraka(int level, int health, int damage) {
        super(level, health, damage);
    }

 
    @Override
    public String getName() {
        return "Baraka";
    }

    /**
     * Возвращает путь к изображению иконки персонажа.
     *
     * @return абсолютный путь к изображению персонажа
     */
    public String getIconSource() {
        return "/baraka.gif";
    }

    /**
     * Возвращает количество очков, получаемых за действия персонажа
     * (например, за победу над ним).
     *
     * @return 110 плюс 30, умноженное на уровень персонажа
     */
    @Override
    public int getReceivedPoints() {
        return 110 + level * 30;
    }

    /**
     * Возвращает количество опыта, которое дается за победу над этим персонажем.
     *
     * @return значение опыта — 20
     */
    @Override
    public int returnExperienceForWin() {
        return 20;
    }

    /**
     * Обновляет характеристики персонажа (здоровье и урон) в зависимости от уровня игрока.
     *
     * @param playerLevel уровень игрока, на основе которого происходит обновление
     */
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel;
        int updatedHealth = 100 + (playerLevel) * 15;
        int updatedDamage = 12 + (playerLevel) * 5;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}