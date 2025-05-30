/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;


import static mephi.b22901.a.l5555.ActionType.ATTACK;
import static mephi.b22901.a.l5555.ActionType.DEBUFF;
import static mephi.b22901.a.l5555.ActionType.DEFEND;
import static mephi.b22901.a.l5555.ActionType.HEAL;
/**
 * Класс {@code Fight} реализует логику пошагового боя между игроком и врагом.
 * Он управляет очередностью ходов, обработкой различных действий и их эффектов,
 * а также ведёт текстовый журнал боя.
 * 
 * @author Andrey
 */
public class Fight {

    /** Флаг, указывающий, чей сейчас ход: {@code TRUE} — ход игрока, {@code FALSE} — ход врага. */
    Boolean isPlayersTurn = true; 
    
    /**
     * Возвращает, чей сейчас ход.
     * 
     * @return {@code true}, если сейчас ход игрока; {@code false} — если врага.
     */
    public boolean getIsPlayerTurn(){
        return this.isPlayersTurn;
    }
    
    
    /**
     * Выполняет действие игрока и возвращает результат в виде строки журнала боя.
     * Если игрок оглушён, ход пропускается с соответствующей обработкой.
     * В противном случае вызывается соответствующий метод обработки действия.
     * 
     * @param human объект игрока
     * @param enemy объект врага
     * @param action действие, выбранное игроком (атака, защита, дебафф)
     * @return строка с логом результата действия и хода врага
     */
    public String performPlayerAction(Player human, Player enemy, ActionType action) {
        String battleLog = "";
        if (isPlayersTurn && human.isStunned()){
            battleLog = processPlayersStun(human, enemy);
        } else {
            switch (action) {
                case ATTACK -> battleLog = processPlayersAttack(human, enemy);
                case DEFEND -> battleLog = processPlayersDefend(human, enemy);
                case DEBUFF -> battleLog = processPlayersDebuff(human, enemy);
            }
        }
        return battleLog;
    }
    
    /**
     * Обрабатывает ситуацию, когда игрок пропускает ход из-за оглушения.
     * Враг атакует, после чего оглушение снимается и ход переходит к врагу.
     * 
     * @param human игрок
     * @param enemy враг
     * @return лог боя с описанием пропуска хода и атаки врага
     */
    private String processPlayersStun(Player human, Player enemy){
        StringBuilder log = new StringBuilder();
        log.append(human.getName()+ " пропускает ход из-за оглушения!\n");
        ActionType enemyBehaviour = ActionType.ATTACK;
        log.append(enemy.getName()+ " выбрал: ").append(enemyBehaviour).append("\n");
        human.setHealth(human.getHealth() - enemy.attackEnemy());
        human.setStunned(false);
        isPlayersTurn = false;
   
        return log.toString();
    }
    
    /**
     * Обрабатывает действие игрока "атака" и последующий ход врага.
     * Логика зависит от состояния оглушения, поведения врага и текущего хода.
     * Ведётся подробный лог, отражающий исходы атак, защит и эффектов.
     * 
     * @param human игрок
     * @param enemy враг
     * @return лог боя
     */
    private String processPlayersAttack(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();    
        if (!isPlayersTurn && enemy.isStunned()) {
            log.append(enemy.getName()+ " пропускает ход из-за оглушения!\n");
            enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy()));
            enemy.setStunned(false);
            isPlayersTurn = true;
            return log.toString();
        } else {
            ActionType enemyBehaviour = AnalystAction.ChooseEnemyBehavior(human, enemy);
            log.append(enemy.getName()+ " выбрал: ").append(enemyBehaviour).append("\n");

            switch (enemyBehaviour) {
                case DEFEND:
                    if (isPlayersTurn){
                        human.setHealth(human.getHealth() - (int) (enemy.attackEnemy() * 0.5));
                        enemy.defendFromEnemy();
                        log.append(enemy.getName()+ " контратаковал!\n");
                        isPlayersTurn = false;
                    } else {
                        human.attackEnemy();
                        enemy.defendFromEnemy();
                        log.append(human.getName()+ " атаковал, но противник заблокировал удар!\n");
                        isPlayersTurn = true;
                    }
                    break;

                case ATTACK:
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy());
                        log.append(human.getName()+ " атаковал, противник получил урон!\n");
                        isPlayersTurn = false;
                    } else {
                        human.setHealth(human.getHealth() - enemy.attackEnemy());
                        log.append(enemy.getName()+ " атаковал, игрок получил урон!\n");
                        isPlayersTurn = true;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy()*2);
                        isPlayersTurn = false;
                    } else {
                        enemy.setHealth(enemy.getHealth() - human.attackEnemy()*2);
                        isPlayersTurn = true;
                    }
                    log.append(enemy.getName()+ " получил двойной урон!\n");
                    break;
                
                case DEBUFF:
                    if (isPlayersTurn){
                        enemy.setHealth((int) (enemy.getHealth() - human.attackEnemy() * 1.15));
                        log.append(enemy.getName()+ " попытался ослабить игрока, но игрок нанес увеличенный урон!\n");
                        isPlayersTurn = false;
                    } else {
                        enemy.setHealth((int) (enemy.getHealth() - human.attackEnemy() * 1.15));
                        log.append(enemy.getName()+ " попытался ослабить игрока, но игрок нанес увеличенный урон!\n");
                        isPlayersTurn = true;
                    }             
                    break;
            }
        }
        return log.toString();
    }
    
    /**
     * Обрабатывает действие игрока "защита" и последующий ход врага.
     * Сложная логика блокирования, контратак, возможных эффектов оглушения и восстановления здоровья.
     * 
     * @param human игрок
     * @param enemy враг
     * @return лог боя
     */
    private String processPlayersDefend(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        if (!isPlayersTurn && enemy.isStunned()) {
            log.append(enemy.getName()+ " пропускает ход из-за оглушения!\n");
            enemy.setStunned(false);
            isPlayersTurn = true;
            log.append("-----------------\n");
            return log.toString();
        } else {
            ActionType enemyBehaviour = AnalystAction.ChooseEnemyBehavior(human, enemy);
            log.append(enemy.getName()+ " выбрал: ").append(enemyBehaviour).append("\n");
            switch (enemyBehaviour) {
                case ATTACK:
                    if (isPlayersTurn){
                        if(enemy instanceof ShaoKahn){
                            double breakingBlockP = Math.random();
                            if(breakingBlockP < 0.15){
                                human.setHealth(human.getHealth() - (int) (human.attackEnemy() * 0.5));
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                log.append("Shao Kahn прорвал блок и нанес урон!\n");
                            } else {
                                enemy.attackEnemy();
                                human.defendFromEnemy();
                                log.append(enemy.getName()+ " атаковал, но игрок заблокировал удар!\n");
                            }
                        } else {
                            enemy.attackEnemy();
                            human.defendFromEnemy();
                             log.append(enemy.getName() + " атаковал, но игрок заблокировал удар!\n");
                        }
                        isPlayersTurn = false;
                    } else {
                        enemy.setHealth(enemy.getHealth() - (int) (human.attackEnemy() * 0.5));
                        log.append(human.getName() + " контратаковал!\n");
                        human.defendFromEnemy();
                        isPlayersTurn = true;
                    }
                    break;

                case DEFEND:
                    if (isPlayersTurn){
                        if (Math.random() < 0.5) {
                            enemy.setStunned(true);
                            log.append(enemy.getName()+ " оглушён!\n");
                        }
                        isPlayersTurn = false;
                    } else {
                        if (Math.random() < 0.5) {
                            human.setStunned(true);
                            log.append(human.getName()+ " оглушён!\n");
                        }
                        isPlayersTurn = true;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                        isPlayersTurn = false;
                    } else {
                        enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                        isPlayersTurn = true;
                    }
                    log.append(enemy.getName()+ " восстановил здоровье!\n");
                    break;
                    
                case DEBUFF:
                    double enemyDebuffProbability = Math.random();
                    if (isPlayersTurn){
                        if(enemyDebuffProbability < 0.75){
                            human.setDebuff(enemy);
                            log.append(human.getName()+ " ослаблен на ").append(enemy.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(human.getName()+ " не был ослаблен!\n");
                        }
                        isPlayersTurn = false;
                    } else {
                        if(enemyDebuffProbability < 0.75){
                            human.setDebuff(enemy);
                            log.append(human.getName()+ " ослаблен на ").append(enemy.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(human.getName()+ " не был ослаблен!\n");
                        }
                        isPlayersTurn = true;
                    }
                    break;
            }
        }
        return log.toString();
    }
    
    /**
     * Обрабатывает действие игрока "дебафф" (ослабление врага) и ход врага.
     * Обрабатываются вероятности успеха дебаффа, лечение и попытки взаимного дебаффа.
     * 
     * @param human игрок
     * @param enemy враг
     * @return лог боя
     */
    private String processPlayersDebuff(Player human, Player enemy) {
        StringBuilder log = new StringBuilder();
        if (!isPlayersTurn && enemy.isStunned()) {
            log.append(enemy.getName()+ " пропускает ход из-за оглушения!\n");
            enemy.setDebuff(human);
            log.append(enemy.getName()+ " ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
            enemy.setStunned(false);
            isPlayersTurn = true;
            return log.toString();
        } else {
            ActionType enemyBehaviour = AnalystAction.ChooseEnemyBehavior(human, enemy);
            log.append(enemy.getName()+ " выбрал: ").append(enemyBehaviour).append("\n");
            double debuffProbability = Math.random();
            switch (enemyBehaviour) {
                case DEFEND:
                    if (isPlayersTurn){
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            log.append(enemy.getName()+ " ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(enemy.getName()+ " не был ослаблен!\n");
                        }
                        isPlayersTurn = false;
                    } else {
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            log.append(enemy.getName()+ " ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(enemy.getName()+ " не был ослаблен!\n");
                        }
                        isPlayersTurn = true;
                    }
                    break;

                case ATTACK:
                    if (isPlayersTurn){
                        human.setHealth((int) (human.getHealth() - enemy.attackEnemy() * 1.15));
                        log.append(human.getName()+" попытался ослабить врага, но враг нанес увеличенный урон!\n");
                        isPlayersTurn = false;
                    } else {
                        human.setHealth((int) (human.getHealth() - enemy.attackEnemy() * 1.15));
                        log.append(human.getName()+" попытался ослабить врага, но враг нанес увеличенный урон!\n");
                        isPlayersTurn = true;
                    }
                    break;
                    
                case HEAL:
                    if (isPlayersTurn){
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                            log.append(enemy.getName()+" восстановил здоровье и был ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(enemy.getName()+" не был ослаблен и восстановил здоровье!\n");
                        }
                        isPlayersTurn = false;
                    } else {
                        if(debuffProbability < 0.75){
                            enemy.setDebuff(human);
                            enemy.setHealth((int) (enemy.getHealth() + (enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
                            log.append(enemy.getName()+" восстановил здоровье и был ослаблен на ").append(human.getLevel()).append(" ходов!").append("\n");
                        } else {
                            log.append(enemy.getName()+" не был ослаблен и восстановил здоровье!\n");
                        }
                        isPlayersTurn = true;
                    }
                    break;
                    
                case DEBUFF:
                    if (isPlayersTurn){
                        log.append(human.getName()+ " и " + enemy.getName()+ " попытались ослабить друг друга => никто не был ослаблен!\n");
                        isPlayersTurn = false;
                    } else {
                        log.append(human.getName()+ " и " + enemy.getName()+ " попытались ослабить друг друга => никто не был ослаблен!\n");
                        isPlayersTurn = true;
                    }
                    break;
            }
        }

        return log.toString();
    }
}