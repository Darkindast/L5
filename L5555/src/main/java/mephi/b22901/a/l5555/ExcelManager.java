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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
 * 
 * @author Arseniy
 */
public class ExcelManager {
    /**
     * Записывает результат игрока в Excel-файл. Если в файле уже есть записи,
     * новые результаты добавляются, сортируются по убыванию очков,
     * и сохраняются только топ-10.
     *
     * @param playerName имя игрока
     * @param playerScore очки игрока
     */
    public static void writeToExcel(String playerName, int playerScore) {
        String filePath = "D:\\L5555\\src\\main\\resourсes\\Results2.xlsx";
        try {
            InputStream is = ExcelManager.class.getResourceAsStream("/Results2.xlsx");
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Row> rows = new ArrayList<>();
            for (Row row : sheet) {
                rows.add(row);
            }

            List<PlayerScoreEntry> entries = new ArrayList<>();
            for (Row row : rows) {
                Cell nameCell = row.getCell(0);
                Cell scoreCell = row.getCell(1);
                if (nameCell != null && scoreCell != null) {
                    String name = nameCell.getStringCellValue();
                    int score = (int) scoreCell.getNumericCellValue();
                    entries.add(new PlayerScoreEntry(name, score));
                }
            }

            entries.add(new PlayerScoreEntry(playerName, playerScore));

            entries.sort((a, b) -> Integer.compare(b.score, a.score));

            if (entries.size() > 10) {
                entries = entries.subList(0, 10);
            }

            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            for (int i = 0; i < entries.size(); i++) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(entries.get(i).name);
                row.createCell(1).setCellValue(entries.get(i).score);
            }

            is.close();

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();
            fos.close();
            System.out.println("Результат записан в Excel.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * Загружает из Excel-файла топ-10 очков игроков.
     * <p>
     * Если в файле меньше 10 записей, возвращается доступное количество.
     * В случае ошибок чтения выводится сообщение об ошибке и возвращается пустой список.
     * </p>
     *
     * @return список топ-10 очков игроков в порядке убывания
     */
    public static List<Integer> loadTop10ScoresFromExcel() {
        List<Integer> scores = new ArrayList<>();
        try (InputStream is = ExcelManager.class.getResourceAsStream("/Results2.xlsx");
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
     * Загружает из Excel-файла топ-10 игроков с их очками в виде списка массивов
     * строк, где каждый массив содержит имя игрока и очки.
     *
     * @return список из массивов {@code String[]}, содержащих имя и очки
     */
    public static List<String[]> loadTop10TableFromExcel() {
        List<String[]> top10 = new ArrayList<>();
        try (InputStream is = ExcelManager.class.getResourceAsStream("/Results2.xlsx");
         Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String name = row.getCell(0).getStringCellValue();
                int score = (int) row.getCell(1).getNumericCellValue();
                top10.add(new String[]{name, String.valueOf(score)});
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return top10;
    }
}