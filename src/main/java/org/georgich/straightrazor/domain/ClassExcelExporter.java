package org.georgich.straightrazor.domain;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClassExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Client> listClients;

    public ClassExcelExporter(List<Client> listClients) {
        this.listClients = listClients;
        workbook = new XSSFWorkbook();
    }

    // creating a sheet and table header
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Клиенты");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Имя", style);
        createCell(row, 1, "Фамилия", style);
        createCell(row, 2, "Сотовый телефон", style);
        createCell(row, 3, "Комментарий", style);

    }

    // method for creating a cell
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    // fill in the table with data for clients
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Client client : listClients) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, client.getName(), style);
            createCell(row, columnCount++, client.getSurname(), style);
            createCell(row, columnCount++, client.getCellNumber(), style);
            createCell(row, columnCount, client.getComment(), style);

        }
    }

    // creating a file, filling it out, and sending it to the user
    public void export(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clients_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
