package com.sberbank.may.student.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс ReportData используется для хранения списка элементов отчета.
 */
@Data
public class ReportData {
    private List<ReportItem> reportItems;
}
