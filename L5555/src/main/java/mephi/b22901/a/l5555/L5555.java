

package mephi.b22901.a.l5555;

import javax.swing.SwingUtilities;

/**
 * Главный класс запуска приложения.
 * 
 * Здесь используется SwingUtilities.invokeLater для того,
 * чтобы создание и отображение главного окна происходило
 * в потоке обработки событий Swing (Event Dispatch Thread),
 * что является хорошей практикой при работе с GUI на Swing.
 */
public class L5555{
     /**
     * Точка входа в программу.
     * Создаёт и отображает главное окно приложения.
     * @param args параметры командной строки (не используются)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FirstFrame mainFrame = new FirstFrame();
            mainFrame.setVisible(true);
        });
    }
}
