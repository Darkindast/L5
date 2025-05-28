/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;
/**
 * Перечисление {@code EnemyType} описывает типы врагов, доступных в игре.
 * Каждый тип может соответствовать определённому стилю боя и характеристикам:
 * <ul>
 *     <li>{@link #TANK} — высокий уровень здоровья, низкий урон (например, Baraka).</li>
 *     <li>{@link #MAGICIAN} — сбалансированный враг с возможностью накладывать эффекты (например, SubZero).</li>
 *     <li>{@link #FIGHTER} — агрессивный враг с высоким уроном (например, LiuKang).</li>
 *     <li>{@link #SOLDIER} — универсальный враг со средними характеристиками (например, SonyaBlade).</li>
 *     <li>{@link #BOSS} — финальный противник с повышенными характеристиками (например, ShaoKahn).</li>
 * </ul>
 * Используется в {@link EnemyFactory} для создания соответствующего врага.
 * 
 * @author Arseniy
 */
public enum EnemyType {
    TANK,
    MAGICIAN,
    FIGHTER,
    SOLDIER,
    BOSS
}