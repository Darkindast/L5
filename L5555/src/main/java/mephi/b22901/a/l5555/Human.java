
package mephi.b22901.a.l5555;
/**
 * Класс Human расширяет Player и представляет игрока-человека в игре.
 * Включает дополнительные поля, такие как очки, опыт, количество побед и инвентарь.\
 *  
 */
public class Human extends Player{
    
    private int points;
    private int experience;
    private int win;
    private int nextexperience;
    private Inventory inventory;
    
    public Human(int level, int health, int  damage){
        super (level, health, damage);
        this.points=0;
        this.experience=0;
        this.nextexperience=40;
        this.win=0;
        inventory = new Inventory();
    }
    
    public Inventory getInventory(){
        return this.inventory;
    }

    public int getPoints(){
        return this.points;
    }
    public int getExperience(){
        return this.experience;
    }

    public int getWin(){
        return this.win;
    }

    public void addPoints(int p){
        this.points+=p;
    }
    public void gainExperience(int e){
        this.experience+=e;
    }
    public void setNextExperience(int e){
        this.nextexperience=e;
    }
    public void addWin(){
        this.winAmount++;
    }
    
    @Override
    public String getName(){
        return "Millena";
    }
    
    @Override
    public String getIconSource(){
        return "/Millena.gif";
    }

    @Override
    public int getReceivedPoints() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public int getRequiredExperience() {
        return this.nextexperience;
    }

    public void setRequiredExperiance() {
        this.nextexperience = nextexperience + 40;
    }

    @Override
    public int returnExperienceForWin() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void updateCharacteristicsBasedOnLevel(int playerLevel) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}