/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.a.l5555;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Класс {@code ExcelManager} отвечает за чтение и запись результатов игроков
 * в файл Excel, расположенный по фиксированному пути.
 * <p>
 * Поддерживает запись нового результата, а также загрузку топ-10 результатов
 * в виде списка очков или таблицы с именами и очками.
 * </p>
 * <p>
 * Для работы требуется библиотека Apache POI.
 * </p>
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelProvider {
    // Путь будет определяться автоматически
    private static String excelFilePath;
    private static boolean fileSelected = false;
    
    // Директория ресурсов (для картинок и Excel-файла)
    private static String resourcesDir;

    static {
        try {
            // Определяем путь к директории ресурсов
            resourcesDir = Paths.get("src", "main", "resources").toAbsolutePath().toString();
            excelFilePath = Paths.get(resourcesDir, "Results2.xlsx").toString();
            
            // Создаем директорию ресурсов, если ее нет
            new File(resourcesDir).mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback - используем текущую директорию
            resourcesDir = System.getProperty("user.dir");
            excelFilePath = Paths.get(resourcesDir, "Results2.xlsx").toString();
        }
    }

    private static class PlayerScoreEntry {
        String name;
        int score;

        PlayerScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    /**
     * Показывает диалог выбора файла
     */
    public static void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser(resourcesDir);
        fileChooser.setDialogTitle("Выберите файл для сохранения результатов");
        fileChooser.setSelectedFile(new File(excelFilePath));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            excelFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            fileSelected = true;
            
            if (!new File(excelFilePath).exists()) {
                createNewExcelFile();
            }
        }
    }

    /**
     * Создает новый Excel-файл
     */
    private static void createNewExcelFile() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(excelFilePath)) {
            workbook.createSheet("Results");
            workbook.write(out);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Не удалось создать файл: " + ex.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Записывает результат игрока в Excel-файл
     */
    public static void writeToExcel(String playerName, int playerScore) {
        if (!fileSelected) {
            showFileChooser();
            if (!fileSelected) return; // Пользователь отменил выбор
        }

        try {
            // Читаем существующий файл или создаем новый
            Workbook workbook;
            Sheet sheet;
            File file = new File(excelFilePath);

            if (file.exists()) {
                try (InputStream is = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(is);
                    sheet = workbook.getSheetAt(0);
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Results");
            }

            // Читаем существующие записи
            List<PlayerScoreEntry> entries = new ArrayList<>();
            for (Row row : sheet) {
                Cell nameCell = row.getCell(0);
                Cell scoreCell = row.getCell(1);
                if (nameCell != null && scoreCell != null) {
                    entries.add(new PlayerScoreEntry(
                            nameCell.getStringCellValue(),
                            (int) scoreCell.getNumericCellValue()
                    ));
                }
            }

            // Добавляем новый результат
            entries.add(new PlayerScoreEntry(playerName, playerScore));

            // Сортируем и оставляем топ-10
            entries.sort((a, b) -> Integer.compare(b.score, a.score));
            if (entries.size() > 10) {
                entries = entries.subList(0, 10);
            }

            // Очищаем лист
            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // Записываем новые данные
            for (int i = 0; i < entries.size(); i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(entries.get(i).name);
                row.createCell(1).setCellValue(entries.get(i).score);
            }

            // Сохраняем
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
            workbook.close();

            System.out.println("Результат записан в Excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает топ-10 очков из Excel-файла
     */
    public static List<Integer> loadTop10ScoresFromExcel() {
        if (!fileSelected) {
            showFileChooser();
            if (!fileSelected) return new ArrayList<>();
        }

        List<Integer> scores = new ArrayList<>();
        try (InputStream is = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell scoreCell = row.getCell(1);
                if (scoreCell != null && scoreCell.getCellType() == CellType.NUMERIC) {
                    scores.add((int) scoreCell.getNumericCellValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении из Excel: " + e.getMessage());
        }
        
        scores.sort(Collections.reverseOrder());
        return scores.size() > 10 ? scores.subList(0, 10) : scores;
    }

    /**
     * Загружает топ-10 игроков с их очками
     */
    public static List<String[]> loadTop10TableFromExcel() {
        if (!fileSelected) {
            showFileChooser();
            if (!fileSelected) return new ArrayList<>();
        }

        List<String[]> top10 = new ArrayList<>();
        try (InputStream is = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell nameCell = row.getCell(0);
                Cell scoreCell = row.getCell(1);
                
                if (nameCell != null && scoreCell != null) {
                    top10.add(new String[]{
                            nameCell.getStringCellValue(),
                            String.valueOf((int) scoreCell.getNumericCellValue())
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Сортируем по убыванию очков
        top10.sort((a, b) -> Integer.compare(
                Integer.parseInt(b[1]),
                Integer.parseInt(a[1])
        ));
        
        return top10.size() > 10 ? top10.subList(0, 10) : top10;
    }
}