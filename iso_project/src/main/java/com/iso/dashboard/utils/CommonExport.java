/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.iso.dashboard.dto.ExcelDTO;
import com.iso.dashboard.dto.SheetDTO;
import com.vaadin.server.VaadinService;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import javax.annotation.CheckForNull;

/**
 * @param <TDTO>
 */
public class CommonExport {
    private static final Logger log = Logger.getLogger(CommonExport.class);

    private CommonExport() {
        //Ham khoi tao
    }

    public static String getTemplateExport() {
        return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + File.separator + "WEB-INF" + File.separator + "templates" + File.separator + "file_export_template"
                //                + File.separator + "TEMPLATE_EXPORT.xls";
                + File.separator + "TEMPLATE_EXPORT.xlsx";
    }

    public static String getTemplateExportMultiSheet() {
        return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + File.separator + "WEB-INF" + File.separator + "templates" + File.separator + "file_export_template"
                //                + File.separator + "TEMPLATE_EXPORT_MULTI_SHEET.xls";
                + File.separator + "TEMPLATE_EXPORT_MULTI_SHEET.xlsx";
    }

    public static String getTemplateMultiExport(String fileName) {
        return VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + File.separator + "WEB-INF" + File.separator + "templates" 
                + File.separator + fileName;

    }

    public static List<AbstractMap.SimpleEntry<String, String>> buildExportHeader(String[] header, String[] align) {
        List<AbstractMap.SimpleEntry<String, String>> headerAlign = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
        for (int i = 0; i < header.length; i++) {
            headerAlign.add(new AbstractMap.SimpleEntry(header[i], align[i]));
        }
        return headerAlign;
    }

    public static List<AbstractMap.SimpleEntry<String, String>> buildExportHeader(String[] header) {
        List<AbstractMap.SimpleEntry<String, String>> headerAlign = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
        for (int i = 0; i < header.length; i++) {
            headerAlign.add(new AbstractMap.SimpleEntry(header[i], "LEFT"));
        }
        return headerAlign;
    }

    public static String getPathSaveFileExport(String fileNameOut) {
        String pathOut;
        pathOut = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + Constants.PATH_OUT;
        File folderOut = new File(pathOut);
        if (!folderOut.exists()) {
            folderOut.mkdir();
        }
        SimpleDateFormat dateFormat = DateUtil.getDateDdMMyyyyHHmmss();
        String strCurTimeExp = dateFormat.format(new Date());
        strCurTimeExp = strCurTimeExp.replaceAll("/", "_");
        strCurTimeExp = strCurTimeExp.replaceAll(Constants.SPACE_CHARACTER, "_");
        strCurTimeExp = strCurTimeExp.replaceAll(":", "_");
        pathOut = pathOut + fileNameOut
                //                + strCurTimeExp + Constants.XLS_FILE_EXTENTION;
                + strCurTimeExp + Constants.XLSX_FILE_EXTENTION;

        return pathOut;
    }

    public static File exportFile(List lstDTO, List<SimpleEntry<String, String>> headerAlign,
                                  String headerPrefix, String pathTemplate, String fileNameOut, int startRow,
                                  String subTitle, int cellTitleIndex, String... title) throws Exception {
        String pathOut = getPathSaveFileExport(fileNameOut);
//        pathOut = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
//                + Constants.PATH_OUT;
//        File folderOut = new File(pathOut);
//        if (!folderOut.exists()) {
//            folderOut.mkdir();
//        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat();
//        dateFormat.applyPattern(DateUtil.getDateDdMMyyyyHHmmss(););
//        String strCurTimeExp = dateFormat.format(new Date());
//        strCurTimeExp = strCurTimeExp.replaceAll("/", "_");
//        strCurTimeExp = strCurTimeExp.replaceAll(Constants.SPACE_CHARACTER, "_");
//        strCurTimeExp = strCurTimeExp.replaceAll(":", "_");
//        pathOut = pathOut + fileNameOut
//                + strCurTimeExp + Constants.XLS_FILE_EXTENTION;
        InputStream fileTemplate = null;
        try {
            fileTemplate = new FileInputStream(pathTemplate);
//            HSSFWorkbook workbook = new HSSFWorkbook(fileTemplate);
//            HSSFSheet worksheet = workbook.getSheetAt(0);
            Workbook workbook = null;
            if (pathTemplate.endsWith(Constants.XLS_FILE_EXTENTION)) {
                workbook = new HSSFWorkbook(fileTemplate);
            } else if (pathTemplate.endsWith(Constants.XLSX_FILE_EXTENTION)) {
                workbook = new XSSFWorkbook(fileTemplate);
            }
            if (workbook == null) {
                throw new Exception("Error to create file export");
            }
            Sheet worksheet = workbook.getSheetAt(0);

            CellStyle cellStyleTitle = setStyleForWorkbook(workbook);
//            HSSFCellStyle cellStyleTitle = setStyleForWorkbook(workbook);
            initDataToSheet(workbook, worksheet, cellStyleTitle, lstDTO,
                    headerAlign, headerPrefix,
                    startRow, subTitle, cellTitleIndex, title);
//            try {

            FileOutputStream fileOut = new FileOutputStream(pathOut);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
//            } catch (IOException e) {
//                log.error(e);
//            }

        } catch (FileNotFoundException e) {
//            PrintLog.printLog(e);
        } finally {
            if (fileTemplate != null) {
                try {
                    fileTemplate.close();
                } catch (Exception ex) {
                    PrintLog.printLog(ex);
                }
            }
        }
//        File file = new File(pathOut);
//        return file;
        return new File(pathOut);

    }

    public static File exportFileMap(List<String> lstDTO, List<SimpleEntry<String, String>> headerAlign,
                                     List<String> headerPrefix, String pathTemplate, String fileNameOut, int startRow,
                                     String subTitle, int cellTitleIndex, String... title) throws Exception {
        String pathOut = getPathSaveFileExport(fileNameOut);

        InputStream fileTemplate = null;
        try {
            fileTemplate = new FileInputStream(pathTemplate);
            Workbook workbook = null;
            if (pathTemplate.endsWith(Constants.XLS_FILE_EXTENTION)) {
                workbook = new HSSFWorkbook(fileTemplate);
            } else if (pathTemplate.endsWith(Constants.XLSX_FILE_EXTENTION)) {
                workbook = new XSSFWorkbook(fileTemplate);
            }
            if (workbook == null) {
                throw new Exception("Error to create file export");
            }
            Sheet worksheet = workbook.getSheetAt(0);

            CellStyle cellStyleTitle = setStyleForWorkbook(workbook);
            initDataToSheetMap(workbook, worksheet, cellStyleTitle, lstDTO,
                    headerAlign, headerPrefix,
                    startRow, subTitle, cellTitleIndex, title);

            FileOutputStream fileOut = new FileOutputStream(pathOut);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (FileNotFoundException e) {
            PrintLog.printLog(e);
        } finally {
            if (fileTemplate != null) {
                try {
                    fileTemplate.close();
                } catch (Exception ex) {
                    PrintLog.printLog(ex);
                }
            }
        }
        return new File(pathOut);

    }

    public static File exportFileMultiSheet(ExcelDTO excel, String pathTemplate, String fileNameOut) throws Exception {
        String pathOut = getPathSaveFileExport(fileNameOut);
        InputStream fileTemplate = null;
        try {
            fileTemplate = new FileInputStream(pathTemplate);
            Workbook workbook = null;
            if (pathTemplate.endsWith(Constants.XLS_FILE_EXTENTION)) {
                workbook = new HSSFWorkbook(fileTemplate);
            } else if (pathTemplate.endsWith(Constants.XLSX_FILE_EXTENTION)) {
                workbook = new XSSFWorkbook(fileTemplate);
            }
//            if (workbook == null) {
//                throw new Exception("Error to export report");
//            }

            if (workbook != null) {
                CellStyle cellStyleTitle = setStyleForWorkbook(workbook);
//            HSSFWorkbook workbook = new HSSFWorkbook(fileTemplate);
//            HSSFCellStyle cellStyleTitle = setStyleForWorkbook(workbook);

                List<SheetDTO> lstAllSheet = excel.getLstDataSheel();
                int i = 0;
                for (SheetDTO sheet : lstAllSheet) {
//                HSSFSheet worksheet = workbook.getSheetAt(i);
                    Sheet worksheet = workbook.getSheetAt(i);
                    initDataToSheet(workbook, worksheet, cellStyleTitle, sheet.getDataSheet(),
                            sheet.getHeaderAlign(), sheet.getHeaderPrefix(),
                            sheet.getStartRow(),
                            sheet.getSubTitle(),
                            sheet.getCellTitleIndex(),
                            sheet.getTitle());
                    i++;
                }
//                try {

                FileOutputStream fileOut = new FileOutputStream(pathOut);
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
//                } catch (IOException e) {
//                    log.error(e);
//                }

            }
        } catch (FileNotFoundException e) {
            log.error(e);
        } finally {
            if (fileTemplate != null) {
                try {
                    fileTemplate.close();
                } catch (Exception ex) {
                    PrintLog.printLog(ex);
                }
            }
        }
//        File file = new File(pathOut);
//        return file;
        return new File(pathOut);

    }

    //    private static HSSFCellStyle setStyleForWorkbook(HSSFWorkbook workbook) {
//        HSSFCellStyle cellStyle;
//
//        HSSFCellStyle cellStyleFormatNumber = workbook.createCellStyle();
//        cellStyleFormatNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
//        cellStyleFormatNumber.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleFormatNumber.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleFormatNumber.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyleFormatNumber.setBorderTop(HSSFCellStyle.BORDER_THIN);
//
//        cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyle.setWrapText(false);
//
//        //Title of report
//        HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
//        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//        HSSFFont hSSFFont = workbook.createFont();
//        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
//        hSSFFont.setFontHeightInPoints((short) 20);
//        hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        hSSFFont.setColor(HSSFColor.BLACK.index);
////            hSSFFont.setColor(HSSFColor.GREEN.index);
//        cellStyleTitle.setFont(hSSFFont);
//        return cellStyleTitle;
//    }
    private static CellStyle setStyleForWorkbook(Workbook workbook) {
        CellStyle cellStyle;

        CellStyle cellStyleFormatNumber = workbook.createCellStyle();
        cellStyleFormatNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        cellStyleFormatNumber.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleFormatNumber.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleFormatNumber.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleFormatNumber.setBorderTop(HSSFCellStyle.BORDER_THIN);

        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setWrapText(false);

        //Title of report
        CellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        Font hSSFFont = workbook.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setFontHeightInPoints((short) 20);
        hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hSSFFont.setColor(HSSFColor.BLACK.index);
//            hSSFFont.setColor(HSSFColor.GREEN.index);
        cellStyleTitle.setFont(hSSFFont);
        return cellStyleTitle;
    }

    //    private static void initDataToSheet(HSSFWorkbook workbook, HSSFSheet worksheet, HSSFCellStyle cellStyleTitle,
//            List lstDTO, List<SimpleEntry<String, String>> headerAlign,
//            String headerPrefix,
//            int startRow, String subTitle, int cellTitleIndex, String... title)
//            throws Exception {
//
//        if (title != null && title.length > 0) {
//            HSSFRow rowMainTitle = worksheet.createRow(startRow - 4);
//            HSSFCell mainCellTitle = rowMainTitle.createCell(cellTitleIndex - 2);
//            mainCellTitle.setCellValue(title[0]);
//            mainCellTitle.setCellStyle(cellStyleTitle);
//            worksheet.addMergedRegion(new CellRangeAddress(startRow - 4, startRow - 4, cellTitleIndex - 2, cellTitleIndex + 2));
//        }
//
//        HSSFRow rowsubTitle = worksheet.createRow(startRow - 2);
//        HSSFCell cellsubTitle = rowsubTitle.createCell(cellTitleIndex);
//        cellsubTitle.setCellValue(subTitle);
//
//        //header
//        HSSFRow rowHeader = worksheet.createRow(startRow);
//        rowHeader.setHeight((short) 500);
//
//        HSSFCellStyle cellStyleHeader = workbook.createCellStyle();
//        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleHeader.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//        cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyleHeader.setWrapText(false);
//        HSSFFont hSSFFontHeader = workbook.createFont();
//        hSSFFontHeader.setFontName(HSSFFont.FONT_ARIAL);
//        hSSFFontHeader.setFontHeightInPoints((short) 10);
//        hSSFFontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        hSSFFontHeader.setColor(HSSFColor.BLUE.index);
//        cellStyleHeader.setFont(hSSFFontHeader);
//
//        for (int i = -1; i < headerAlign.size(); i++) {
//            HSSFCell cellHeader = rowHeader.createCell(i + 1);
//            if (i == -1) {
//                cellHeader.setCellValue(BundleUtils.getString("STT"));
//            } else {
//                SimpleEntry<String, String> entry = headerAlign.get(i);
//                cellHeader.setCellValue(BundleUtils.getString(headerPrefix + "." + entry.getKey()));
//
//            }
//            cellHeader.setCellStyle(cellStyleHeader);
//        }
//
//        //trai
//        HSSFCellStyle cellStyleLeft = workbook.createCellStyle();
//        cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//        cellStyleLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyleLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyleLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleLeft.setWrapText(false);
//        //phai
//        HSSFCellStyle cellStyleRight = workbook.createCellStyle();
//        cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        cellStyleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyleRight.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleRight.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyleRight.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleRight.setWrapText(false);
//        //giua
//        HSSFCellStyle cellStyleCenter = workbook.createCellStyle();
//        cellStyleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        cellStyleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleCenter.setWrapText(false);
//
//        //Data
//        if (lstDTO != null && !lstDTO.isEmpty()) {
//            //init mapColumn
//            Object firstRow = lstDTO.get(0);
//            Map<String, Field> mapField = new HashMap<String, Field>();
//            for (int j = 0; j < headerAlign.size(); j++) {
//                SimpleEntry<String, String> entryHeader = headerAlign.get(j);
//                String header = entryHeader.getKey();
//                for (Field f : firstRow.getClass().getDeclaredFields()) {
//                    f.setAccessible(true);
//                    if (f.getName().equals(header)) {
//                        mapField.put(header, f);
//                    }
//                }
//            }
//
//            //fillData
//            for (int i = 0; i < lstDTO.size(); i++) {
//                HSSFRow row = worksheet.createRow(i + startRow + 1);
//                for (int j = -1; j < headerAlign.size(); j++) {
//                    HSSFCell cell = row.createCell(j + 1);
//                    if (j == -1) {
//                        cell.setCellValue(i + 1);
//                        cell.setCellStyle(cellStyleCenter);
//                    } else {
//                        SimpleEntry<String, String> entryHeader = headerAlign.get(j);
//                        String header = entryHeader.getKey();
//                        String align = entryHeader.getValue();
//                        Object obj = lstDTO.get(i);
//                        Field f = mapField.get(header);
////                            f.setAccessible(true);
//                        if (f.getName().equals(header)) {
//                            Object value = f.get(obj);
//                            cell.setCellValue(value == null ? Constants.EMPTY_CHARACTER : value.toString());
//                            if ("CENTER".equals(align)) {
//                                cell.setCellStyle(cellStyleCenter);
//                            }
//                            if ("LEFT".equals(align)) {
//                                cell.setCellStyle(cellStyleLeft);
//                            }
//                            if ("RIGHT".equals(align)) {
//                                cell.setCellStyle(cellStyleRight);
//                            }
//                        }
//
//                    }
//                }
//
//            }
//        }
//
//        //Set Width
//        for (int i = 0; i <= headerAlign.size(); i++) {
//            worksheet.autoSizeColumn(i);
//            if (worksheet.getColumnWidth(i) > Constants.NUMBER_PART + 1) {
//                worksheet.setColumnWidth(i, Constants.NUMBER_PART + 1);
//            }
//        }
//    }
    private static void initDataToSheet(Workbook workbook,
                                        Sheet worksheet, CellStyle cellStyleTitle,
                                        List lstDTO, List<SimpleEntry<String, String>> headerAlign,
                                        String headerPrefix,
                                        int startRow, String subTitle, int cellTitleIndex, String... title)
            throws Exception {

        if (title != null && title.length > 0) {
            Row rowMainTitle = worksheet.createRow(startRow - 4);
            Cell mainCellTitle = rowMainTitle.createCell(cellTitleIndex - 2);
            mainCellTitle.setCellValue(title[0]);
            mainCellTitle.setCellStyle(cellStyleTitle);
            worksheet.addMergedRegion(new CellRangeAddress(startRow - 4, startRow - 4, cellTitleIndex - 2, cellTitleIndex + 2));
        }
        if (!DataUtil.isNullOrEmpty(subTitle)) {
            Row rowsubTitle = worksheet.createRow(startRow - 2);
            Cell cellsubTitle = rowsubTitle.createCell(cellTitleIndex);
            cellsubTitle.setCellValue(subTitle);
        }

        //header
        Row rowHeader = worksheet.createRow(startRow);
        rowHeader.setHeight((short) 500);

        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleHeader.setWrapText(false);
        Font hSSFFontHeader = workbook.createFont();
        hSSFFontHeader.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFontHeader.setFontHeightInPoints((short) 10);
        hSSFFontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hSSFFontHeader.setColor(HSSFColor.BLUE.index);
        cellStyleHeader.setFont(hSSFFontHeader);

        for (int i = -1; i < headerAlign.size(); i++) {
            Cell cellHeader = rowHeader.createCell(i + 1);
            if (i == -1) {
                cellHeader.setCellValue(BundleUtils.getString("STT"));
            } else {
                SimpleEntry<String, String> entry = headerAlign.get(i);
                cellHeader.setCellValue(BundleUtils.getString(headerPrefix + "." + entry.getKey()));

            }
            cellHeader.setCellStyle(cellStyleHeader);
        }

        //trai
        CellStyle cellStyleLeft = workbook.createCellStyle();
        cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setWrapText(false);
        //phai
        CellStyle cellStyleRight = workbook.createCellStyle();
        cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleRight.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setWrapText(false);
        //giua
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setWrapText(false);

        //Data
        if (lstDTO != null && !lstDTO.isEmpty()) {
            //init mapColumn
            Object firstRow = lstDTO.get(0);
            Map<String, Field> mapField = new HashMap<String, Field>();
            for (int j = 0; j < headerAlign.size(); j++) {
                SimpleEntry<String, String> entryHeader = headerAlign.get(j);
                String header = entryHeader.getKey();
                
                Field[] fs = ReflectorUtil.getAllFields(firstRow.getClass());
                for (Field f : fs) {
//                for (Field f : firstRow.getClass().getDeclaredFields()) {
                    
                    f.setAccessible(true);
                    if (f.getName().equals(header)) {
                        mapField.put(header, f);
                    }
                }
            }

            //fillData
            for (int i = 0; i < lstDTO.size(); i++) {
                Row row = worksheet.createRow(i + startRow + 1);
                for (int j = -1; j < headerAlign.size(); j++) {
                    Cell cell = row.createCell(j + 1);
                    if (j == -1) {
                        cell.setCellValue(i + 1);
                        cell.setCellStyle(cellStyleCenter);
                    } else {
                        SimpleEntry<String, String> entryHeader = headerAlign.get(j);
                        String header = entryHeader.getKey();
                        String align = entryHeader.getValue();
                        Object obj = lstDTO.get(i);
                        Field f = mapField.get(header);
//                            f.setAccessible(true);
                        if (f.getName().equals(header)) {
                            Object value = f.get(obj);
                            cell.setCellValue(value == null ? Constants.EMPTY_CHARACTER : value.toString());
                            if ("CENTER".equals(align)) {
                                cell.setCellStyle(cellStyleCenter);
                            }
                            if ("LEFT".equals(align)) {
                                cell.setCellStyle(cellStyleLeft);
                            }
                            if ("RIGHT".equals(align)) {
                                cell.setCellStyle(cellStyleRight);
                            }
                        }

                    }
                }

            }
        }

        //Set Width
        //_comment vi doan nay anh huong hieu nang_start
//        for (int i = 0; i <= headerAlign.size(); i++) {
//            worksheet.autoSizeColumn(i);
//            if (worksheet.getColumnWidth(i) > Constants.NUMBER_PART + 1) {
//                worksheet.setColumnWidth(i, Constants.NUMBER_PART + 1);
//            }
//        }
        //_comment vi doan nay anh huong hieu nang_end
    }

    private static void initDataToSheetMap(Workbook workbook,
                                           Sheet worksheet, CellStyle cellStyleTitle,
                                           List<String> lstDTO, List<SimpleEntry<String, String>> headerAlign,
                                           List<String> headerPrefix,
                                           int startRow, String subTitle, int cellTitleIndex, String... title)
            throws Exception {

        if (title != null && title.length > 0) {
            Row rowMainTitle = worksheet.createRow(startRow - 4);
            Cell mainCellTitle = rowMainTitle.createCell(cellTitleIndex - 2);
            mainCellTitle.setCellValue(title[0]);
            mainCellTitle.setCellStyle(cellStyleTitle);
            worksheet.addMergedRegion(new CellRangeAddress(startRow - 4, startRow - 4, cellTitleIndex - 2, cellTitleIndex + 2));
        }
        if (!DataUtil.isNullOrEmpty(subTitle)) {
            Row rowsubTitle = worksheet.createRow(startRow - 2);
            Cell cellsubTitle = rowsubTitle.createCell(cellTitleIndex);
            cellsubTitle.setCellValue(subTitle);
        }

        //header
        Row rowHeader = worksheet.createRow(startRow);
        rowHeader.setHeight((short) 500);

        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleHeader.setWrapText(false);
        Font hSSFFontHeader = workbook.createFont();
        hSSFFontHeader.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFontHeader.setFontHeightInPoints((short) 10);
        hSSFFontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hSSFFontHeader.setColor(HSSFColor.BLUE.index);
        cellStyleHeader.setFont(hSSFFontHeader);

        for (int i = 0; i < headerAlign.size(); i++) {
            Cell cellHeader = rowHeader.createCell(i);
//            if (i == -1) {
//                cellHeader.setCellValue(BundleUtils.getString("STT"));
//            } else {
                cellHeader.setCellValue(headerPrefix.get(i));

//            }
            cellHeader.setCellStyle(cellStyleHeader);
        }

        //trai
        CellStyle cellStyleLeft = workbook.createCellStyle();
        cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleLeft.setWrapText(false);
        //phai
        CellStyle cellStyleRight = workbook.createCellStyle();
        cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyleRight.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleRight.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleRight.setWrapText(false);
        //giua
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleCenter.setWrapText(false);

        //Data
        if (lstDTO != null && !lstDTO.isEmpty()) {


            //fillData
            for (int i = 0; i < lstDTO.size(); i++) {
                Row row = worksheet.createRow(i + startRow + 1);
                for (int j = 0; j < headerAlign.size(); j++) {
                    Cell cell = row.createCell(j);
                    if (j == 0) {
                        cell.setCellValue(i + 1);
                        cell.setCellStyle(cellStyleCenter);
                    } else {
                        SimpleEntry<String, String> entryHeader = headerAlign.get(j);
                        String header = entryHeader.getKey();
                        String align = entryHeader.getValue();
                        Map<String, Object> result = AdminCfgUtil.getInstance().jsonStringToMap(lstDTO.get(i));
//                        Field f = mapField.get(header);
//                        if (f.getName().equals(header)) {
                        Object value = result.get(header);
                        cell.setCellValue(value == null ? Constants.EMPTY_CHARACTER : value.toString());
                        if ("CENTER".equals(align)) {
                            cell.setCellStyle(cellStyleCenter);
                        }
                        if ("LEFT".equals(align)) {
                            cell.setCellStyle(cellStyleLeft);
                        }
                        if ("RIGHT".equals(align)) {
                            cell.setCellStyle(cellStyleRight);
                        }
//                        }

                    }
                }

            }
        }

    }

    public static String getPathSaveFile(String fileNameOut, String extension) {
        String pathOut;
        pathOut = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + Constants.PATH_OUT;
        File folderOut = new File(pathOut);
        if (!folderOut.exists()) {
            folderOut.mkdir();
        }
        SimpleDateFormat dateFormat = DateUtil.getDateDdMMyyyyHHmmss();
        String strCurTimeExp = dateFormat.format(new Date());
        strCurTimeExp = strCurTimeExp.replaceAll("/", "_");
        strCurTimeExp = strCurTimeExp.replaceAll(Constants.SPACE_CHARACTER, "_");
        strCurTimeExp = strCurTimeExp.replaceAll(":", "_");
        pathOut = pathOut + fileNameOut
                //                + strCurTimeExp + Constants.XLS_FILE_EXTENTION;
                + strCurTimeExp + extension;

        return pathOut;
    }

    public static String getPath() {
        String pathOut;
        pathOut = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                + Constants.PATH_OUT;
        File folderOut = new File(pathOut);
        if (!folderOut.exists()) {
            folderOut.mkdir();
        }

        return pathOut;
    }

    public static String getSaveFile(String fileNameOut, String extension) {
        String pathOut = Constants.EMPTY_CHARACTER;
        SimpleDateFormat dateFormat = DateUtil.getDateDdMMyyyyHHmmss();
        String strCurTimeExp = dateFormat.format(new Date());
        strCurTimeExp = strCurTimeExp.replaceAll("/", "_");
        strCurTimeExp = strCurTimeExp.replaceAll(Constants.SPACE_CHARACTER, "_");
        strCurTimeExp = strCurTimeExp.replaceAll(":", "_");
        pathOut = pathOut + fileNameOut + strCurTimeExp + extension;

        return pathOut;
    }

    //anhmv6_13/06/2016_xuat file csv_start
//    public static File exportFileCsv(List lstDTO, List<SimpleEntry<String, String>> headerAlign,
//            String headerPrefix, String fileNameOut,
//            String subTitle, String... title) throws Exception {
//        String pathOut = getSaveFile(fileNameOut, Constants.CSV_FILE_EXTENTION);
//        try {
//            List<StringBuilder> lstSb = new ArrayList<>();
//            int size = lstSb.size();
//            List temp;
//            StringBuilder sb1 = new StringBuilder();
//            if (title != null && title.length > 0) {
//                sb1.append("\t").append(title[0]).append("\r\n\r\n");
//            }
//            sb1.append(subTitle).append("\r\n");
//
//            //header
//            for (int i = -1; i < headerAlign.size(); i++) {
//                if (i == -1) {
//                    sb1.append(BundleUtils.getString("STT")).append(Constants.COMMA);
//                } else {
//                    SimpleEntry<String, String> entry = headerAlign.get(i);
//                    sb1.append(BundleUtils.getString(headerPrefix + "." + entry.getKey())).append(Constants.COMMA);
//                }
//            }
//            sb1.append("\r\n");
//            lstSb.add(sb1);
//
//            if (size > 10000) {
//                int i = 0;
//                int j = 9999;
//                while (i <= size) {
//                    log.info("i : " + i + ", j : " + j);
//                    temp = lstDTO.subList(i, j);
//                    StringBuilder sb = new StringBuilder();
//                    initDataToFileCsv(sb, temp,
//                            headerAlign, headerPrefix,
//                            subTitle, title);
//                    lstSb.add(sb);
//                    log.info("lstDTO.size() : " + lstDTO.size());
//                    i = j;
//                    if (size - j > 9999) {
//                        j += 10000;
//                    } else if (j == size) {
//                        j += 1;
//                    } else {
//                        j += size - i + 1;
//                    }
//                }
//            } else {
//                StringBuilder sb = new StringBuilder();
//                initDataToFileCsv(sb, lstDTO,
//                        headerAlign, headerPrefix,
//                        subTitle, title);
//                lstSb.add(sb);
//            }
////            StringBuilder sb = new StringBuilder();
////            initDataToFileCsv(sb, lstDTO,
////                    headerAlign, headerPrefix,
////                    subTitle, title);
//            long start = System.currentTimeMillis();
//            FileOutputStream fileOut = new FileOutputStream(pathOut);
//
////            try {
////                fileOut.write(s.getBytes(Charset.forName("UTF-8")));
////            } catch (Exception e) {
////                log.error(e);
////            } finally {
////                fileOut.flush();
////                fileOut.close();
////            }
//
//            /*
//             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut, "UTF-8");
//             try (BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
//             bufferedWriter.write(s);
//             }*/
////                byte[] buffer = sb.toString().getBytes();
////                FileChannel rwChannel = new RandomAccessFile(pathOut, "rw").getChannel();
////                ByteBuffer wrBuf = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length);
////                wrBuf.put(buffer);
////                rwChannel.close();
////            File file = new File(pathOut);
////            FileWriter writer = new FileWriter(file);
////            try (BufferedWriter bufferedWriter = new BufferedWriter(writer, 8192)) {
////                bufferedWriter.write(sb.toString());
////                bufferedWriter.flush();
////            }
//            Writer out = new BufferedWriter(new OutputStreamWriter(fileOut, "UTF-8"));
//            try {
//                for (StringBuilder sb : lstSb) {
//                    out.write(sb.toString());
//                }
//            } finally {
//                out.close();
//            }
//            log.info("Time : " + (System.currentTimeMillis() - start));
//
//        } catch (Exception e) {
//            log.error(e);
//        }
////        File file = new File(pathOut);
////        return file;
//        return new File(pathOut);
//
//    }

    private static void initDataToFileCsv(
            StringBuilder sb, List lstDTO,
            List<SimpleEntry<String, String>> headerAlign,
            String headerPrefix,
            String subTitle, String... title) {
//        if (title != null && title.length > 0) {
//            sb.append("\t").append(title[0]).append("\r\n\r\n");
//        }
//        sb.append(subTitle).append("\r\n");
//
//        //header
//        for (int i = -1; i < headerAlign.size(); i++) {
//            if (i == -1) {
//                sb.append(BundleUtils.getString("STT")).append(Constants.COMMA);
//            } else {
//                SimpleEntry<String, String> entry = headerAlign.get(i);
//                sb.append(BundleUtils.getString(headerPrefix + "." + entry.getKey())).append(Constants.COMMA);
//            }
//        }
//        sb.append("\r\n");

        //Data
        if (lstDTO != null && !lstDTO.isEmpty()) {
            //init mapColumn
            Object firstRow = lstDTO.get(0);
            Map<String, Field> mapField = new HashMap<>();
            for (int j = 0; j < headerAlign.size(); j++) {
                SimpleEntry<String, String> entryHeader = headerAlign.get(j);
                String header = entryHeader.getKey();
                for (Field f : firstRow.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (f.getName().equals(header)) {
                        mapField.put(header, f);
                    }
                }
            }

            try {
                //fillData
                for (int i = 0; i < lstDTO.size(); i++) {
                    for (int j = -1; j < headerAlign.size(); j++) {
                        if (j == -1) {
                            sb.append(i + 1).append(Constants.COMMA);
                        } else {
                            SimpleEntry<String, String> entryHeader = headerAlign.get(j);
                            String header = entryHeader.getKey();
                            Object obj = lstDTO.get(i);
                            Field f = mapField.get(header);
                            if (f.getName().equals(header)) {
                                Object value = f.get(obj);
                                sb.append(value == null ? Constants.EMPTY_CHARACTER : value.toString()).append(Constants.COMMA);
                            }

                        }
                    }
                    sb.append(subTitle).append("\r\n");
                }
            } catch (Exception e) {
                log.error(e);
            }

        }
    }

    //anhmv6_13/06/2016_xuat file csv_end
}
