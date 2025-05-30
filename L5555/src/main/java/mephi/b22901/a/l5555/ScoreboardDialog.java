
package mephi.b22901.a.l5555;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 * Диалоговое окно для отображения таблицы рекордов (топ-10).
 * Данные загружаются из Excel через ExcelManager.
 * В окне есть JTable для отображения результатов и кнопка "Закрыть".
 * При закрытии окна создаётся и показывается главное окно MainFrame.
 */
public class ScoreboardDialog extends JDialog {

    private JButton btnClose;
    private JTable table;
    private String[] columnNames = {"Место", "Имя", "Результат"};
    private final FirstFrame mainFrame;

    public ScoreboardDialog(FirstFrame parent) {
        super(parent, "Таблица рекордов", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        this.mainFrame = parent;
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setupActions();
    }
    
    /**
     * Создание таблицы и кнопки, добавление их на форму.
     * Загружает данные из ExcelManager и формирует массив для JTable.
     */
    private void initializeComponents() {
        List<String[]> scoreDataList = ExcelProvider.loadTop10TableFromExcel();
        String[][] scoreData = new String[scoreDataList.size()][3];
        for (int i = 0; i < scoreDataList.size(); i++) {
            scoreData[i][0] = String.valueOf(i + 1);
            scoreData[i][1] = scoreDataList.get(i)[0];
            scoreData[i][2] = scoreDataList.get(i)[1];
        }

        table = new JTable(new DefaultTableModel(scoreData, columnNames));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        btnClose = new JButton("Закрыть");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnClose);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActions() {
        btnClose.addActionListener(this::onCloseClicked);
    }

    public void onCloseClicked(ActionEvent e) {
        dispose();
    }   
}