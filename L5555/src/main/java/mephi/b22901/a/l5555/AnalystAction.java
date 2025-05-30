/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import mephi.b22901.a.l5555.ActionType;

/**
 * Класс {@code AnalystAction} содержит статические методы,
 * определяющие поведение врагов в бою на основе анализа действий игрока.
 * Используется для реализации ИИ-противников с адаптивной стратегией.
 * <p>
 * Методы анализируют агрессивность и защитную тактику игрока и 
 * соответственно выбирают тип действия (атака, защита, лечение, дебафф).
 * </p>
 * 
 */
public class AnalystAction {
     /**
     * Выбирает тип действия (атака или защита) на основе вероятностей
     * и поведения игрока (количества атак и защит).
     * <p>
     * Для первых 10 действий применяется базовое вероятностное поведение,
     * после чего поведение врага подстраивается под стиль игрока.
     * </p>
     *
     * @param attackProbability базовая вероятность атаки (в процентах)
     * @param defendProbability базовая вероятность защиты (в процентах)
     * @param human объект игрока, чье поведение анализируется
     * @return {@link ActionType#ATTACK} или {@link ActionType#DEFEND}
     */
    public static ActionType adaptiveBehaviourChooser(int attackProbability, int defendProbability, Player human){
        int attackCount = human.getAttackCount();
        int defendCount = human.getDefendCount();
        int total = attackCount + defendCount;

        if (total < 10) {
            return Math.random() < attackProbability / 100.0 ? ActionType.ATTACK : ActionType.DEFEND;
        } else{
            double playerAggression = (double) attackCount / total;
            double playerDefense = (double) defendCount / total;

            int adjustedAttackProb = (int) (attackProbability * playerDefense + defendProbability * playerDefense * 0.5);
            int adjustedDefendProb = (int) (defendProbability * playerAggression + attackProbability * playerAggression * 0.5);

            int totalAdjusted = adjustedAttackProb + adjustedDefendProb;
            double normAttack = (double) adjustedAttackProb / totalAdjusted;

            return Math.random() < normAttack ? ActionType.ATTACK : ActionType.DEFEND;
        }
    }
    
    /**
     * Определяет поведение врага в бою на основе его типа и состояния игрока.
     * Для каждого типа врага используется своя стратегия выбора действия,
     * включая вероятности дебаффа и лечения.
     *
     * @param human игрок, против которого действует враг
     * @param enemy враг, поведение которого выбирается
     * @return выбранное действие врага в виде {@link ActionType}
     */
    public static ActionType ChooseEnemyBehavior(Player human, Player enemy) {
        ActionType enemyAction = null;
        if (enemy instanceof Baraka) {
            enemyAction = adaptiveBehaviourChooser(30, 70, human);
        }
        if (enemy instanceof SubZero) {
            double debuffProbability = Math.random();
            if(debuffProbability < 0.25 && human.getDebuff() == 0 && enemy.getLevel() != 0 && enemy.getDebuff() == 0){
                enemyAction = ActionType.DEBUFF;
            } else {
                enemyAction = adaptiveBehaviourChooser(40, 60, human);
            } 
        }
        if (enemy instanceof LiuKang) {
            enemyAction = adaptiveBehaviourChooser(50, 50, human);
        }
        if (enemy instanceof SonyaBlade) {
            enemyAction = adaptiveBehaviourChooser(60, 40, human);
        }
        if (enemy instanceof ShaoKahn) {
            double healProbability = Math.random();
            if(healProbability < 0.3 && enemy.getMaxHealth() != enemy.getHealth()){
                enemyAction = ActionType.HEAL;
            } else {
                enemyAction = adaptiveBehaviourChooser(45, 55, human);
            }
        }
        return enemyAction;
    }
}