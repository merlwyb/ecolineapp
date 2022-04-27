///*
// * Copyright (c) 2008-2020
// * LANIT
// * All rights reserved.
// *
// * This product and related documentation are protected by copyright and
// * distributed under licenses restricting its use, copying, distribution, and
// * decompilation. No part of this product or related documentation may be
// * reproduced in any form by any means without prior written authorization of
// * LANIT and its licensors, if any.
// *
// * $
// */
//package com.ecoline.application.data.service;
//
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.*;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.time.Duration;
//import java.time.Month;
//import java.time.YearMonth;
//import java.time.format.TextStyle;
//import java.util.*;
//
//@Service
//public class AttendanceReportServiceImpl{
//    private static final int SHEET_EMPLOYEE_HOURS_AT_OFFICE = 0;
//    private static final int SHEET_SOURCE_DATA = 1;
//
//    private static final int COLUMN_EMPLOYEE_FULL_NAME = 0;
//    private static final int COLUMN_ENTRANCE_TIME = 1;
//    private static final int COLUMN_EXIT_TIME = 2;
//    private static final int COLUMN_EMPLOYEE_OFFICE_ID = 3;
//    private static final int COLUMN_OFFICE_ID = 5;
//    private static final int COLUMN_OFFICE_NAME = 6;
//
//    private static final int ROW_WITH_MONTH_YEAR = 0;
//    private static final int CELL_MONTH = 1;
//    private static final int CELL_YEAR = 3;
//
//    private static final int COLUMN_EMPLOYEE_HOURS = 1;
//    private static final int COLUMN_EMPLOYEE_OFFICE_NAME = 2;
//
//    AttendanceService attendanceService;
//
//    public AttendanceReportServiceImpl(AttendanceService attendanceService) {
//        this.attendanceService = attendanceService;
//    }
//
//    @Override
//    public Workbook createAttendanceReport(Month month, int year) {
//        InputStream attendanceTemplate = getClass().getResourceAsStream("/reports/attendance.xlsx");
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(attendanceTemplate);
//            List<Attendance> attendances = attendanceService.findAllByMonth(YearMonth.of(year, month));
//
//            fillSourceDataSheet(workbook, attendances);
//
//            Comparator<Attendance> comparator = new OfficeNameComparator().thenComparing(new EmployeeNameComparator());
//            attendances.sort(comparator);
//
//            Map<EmployeeFullNameWithOfficeName, Double> employeeOfficeWorkTimeMap = new LinkedHashMap<>();
//            for (Attendance att : attendances) {
//                EmployeeFullNameWithOfficeName employeeOffice = new EmployeeFullNameWithOfficeName(att.getEmployee().getFullName(), att.getOffice().getName());
//                employeeOfficeWorkTimeMap.merge(employeeOffice,
//                    getDurationInHours(att), Double::sum
//                );
//            }
//
//            fillEmployeeHoursSheet(workbook, employeeOfficeWorkTimeMap, month, year);
//
//            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
//            return workbook;
//        } catch (IOException | IllegalStateException e) {
//            throw new IllegalStateException("Error in attendance report", e);
//        }
//    }
//
//    // ===================================================================================================================
//    // = Implementation
//    // ===================================================================================================================
//
//    private void fillSourceDataSheet(XSSFWorkbook workbook, List<Attendance> attendances) {
//        XSSFSheet sheet = workbook.getSheetAt(SHEET_SOURCE_DATA);
//        XSSFRow xssfRow;
//        XSSFCell xssfCell;
//
//        Set<Office> offices = new HashSet<>();
//        int rowNumber = 0;
//        CellStyle dateTimeStyle = workbook.createCellStyle();
//        dateTimeStyle.setDataFormat(workbook.getCreationHelper()
//            .createDataFormat().getFormat("dd.mm.yy hh:mm:ss")
//        );
//        for (Attendance att : attendances) {
//            rowNumber++;
//            xssfRow = sheet.createRow(rowNumber);
//            xssfRow.createCell(COLUMN_EMPLOYEE_FULL_NAME)
//                .setCellValue(att.getEmployee().getFullName());
//
//            xssfCell = xssfRow.createCell(COLUMN_ENTRANCE_TIME);
//            xssfCell.setCellStyle(dateTimeStyle);
//            xssfCell.setCellValue(att.getEntranceTime());
//
//            xssfCell = xssfRow.createCell(COLUMN_EXIT_TIME);
//            xssfCell.setCellStyle(dateTimeStyle);
//            xssfCell.setCellValue(att.getExitTime());
//
//            xssfRow.createCell(COLUMN_EMPLOYEE_OFFICE_ID)
//                .setCellValue(att.getOffice().getId());
//            offices.add(att.getOffice());
//        }
//
//        rowNumber = 0;
//        for (Office off : offices) {
//            rowNumber++;
//            xssfRow = sheet.getRow(rowNumber);
//            xssfRow.createCell(COLUMN_OFFICE_ID)
//                .setCellValue(off.getId());
//            xssfRow.createCell(COLUMN_OFFICE_NAME)
//                .setCellValue(off.getName());
//        }
//    }
//
//    private void fillEmployeeHoursSheet(XSSFWorkbook workbook, Map<EmployeeFullNameWithOfficeName, Double> employeeOfficeWorkTimeMap, Month month, int year) {
//        XSSFSheet sheet = workbook.getSheetAt(SHEET_EMPLOYEE_HOURS_AT_OFFICE);
//        XSSFRow xssfRow;
//        xssfRow = sheet.getRow(ROW_WITH_MONTH_YEAR);
//        xssfRow.getCell(CELL_MONTH)
//            .setCellValue(month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")));
//        xssfRow.getCell(CELL_YEAR)
//            .setCellValue(year);
//
//        int rowNumber = 2;
//        for (EmployeeFullNameWithOfficeName employeeOffice : employeeOfficeWorkTimeMap.keySet()) {
//            rowNumber++;
//            xssfRow = sheet.createRow(rowNumber);
//            xssfRow.createCell(COLUMN_EMPLOYEE_FULL_NAME)
//                .setCellValue(employeeOffice.employeeFullName);
//            xssfRow.createCell(COLUMN_EMPLOYEE_HOURS)
//                .setCellValue((int) Math.ceil(employeeOfficeWorkTimeMap.get(employeeOffice)));
//            xssfRow.createCell(COLUMN_EMPLOYEE_OFFICE_NAME)
//                .setCellValue(employeeOffice.officeName);
//        }
//    }
//
//    double getDurationInHours(Attendance attendance) {
//        return ((double) Duration.between(attendance.getEntranceTime(), attendance.getExitTime()).toMinutes()) / 60;
//    }
//
//    static class EmployeeFullNameWithOfficeName {
//        private final String employeeFullName;
//        private final String officeName;
//
//        public EmployeeFullNameWithOfficeName(String employeeFullName, String officeName) {
//            this.employeeFullName = employeeFullName;
//            this.officeName = officeName;
//        }
//
//        @Override
//        public int hashCode() {
//            return employeeFullName.hashCode() + officeName.hashCode();
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            return this.getClass() == obj.getClass() && ((this.employeeFullName.equals(((EmployeeFullNameWithOfficeName) obj).employeeFullName)) &&
//                (this.officeName.equals(((EmployeeFullNameWithOfficeName) obj).officeName)));
//        }
//    }
//
//    static class OfficeNameComparator implements Comparator<Attendance> {
//        private static final String[] officeNameOrder = new String[]{"Москва", "Уфа", "Нижний Новгород"};
//        private static final Map<String, Integer> officeNamePriorityMap = new HashMap<>();
//
//        public OfficeNameComparator() {
//            int priority = 0;
//            for (String officeName : officeNameOrder) {
//                priority++;
//                officeNamePriorityMap.put(officeName, priority);
//            }
//        }
//
//        @Override
//        public int compare(Attendance att1, Attendance att2) {
//            int officeName1_priority = officeNamePriorityMap.getOrDefault(att1.getOffice().getName(), 0);
//            int officeName2_priority = officeNamePriorityMap.getOrDefault(att2.getOffice().getName(), 0);
//            return Integer.compare(officeName1_priority, officeName2_priority);
//        }
//    }
//
//    static class EmployeeNameComparator implements Comparator<Attendance> {
//        @Override
//        public int compare(Attendance att1, Attendance att2) {
//            return att1.getEmployee().getFullName().compareTo(att2.getEmployee().getFullName());
//        }
//    }
//}
