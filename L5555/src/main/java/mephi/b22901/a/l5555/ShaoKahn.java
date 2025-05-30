
package mephi.b22901.a.l5555;
/**
 * Класс, представляющий босса Shao Kahn.
 * Характеристики зависят от уровня игрока + 2.
 */
public class ShaoKahn extends Player{
    
    public ShaoKahn(int level, int health, int  damage){
        super (level, health, damage);
    }
    
    @Override
    public String getName(){
        return "Shao Kahn";
    }
    
    @Override
    public String getIconSource(){
        return "/shao kahn.gif";
    }

    @Override
    public int getReceivedPoints() {
        return 250 + level*50;
    }
    
    @Override
    public int returnExperienceForWin() {
        return 40;
    }
    
    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        this.level = playerLevel + 2;
        int updatedHealth = 145 + (playerLevel) * 20;
        int updatedDamage = 44 + (playerLevel) * 15;
        this.health = updatedHealth;
        this.damage = updatedDamage;
        this.maxHealth = updatedHealth;
        this.maxDamage = updatedDamage;
    }
}