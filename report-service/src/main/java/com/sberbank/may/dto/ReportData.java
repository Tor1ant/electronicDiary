package com.sberbank.may.dto;

import java.util.List;
import lombok.Data;

/**
 * Класс ReportData используется для хранения списка элементов отчета.
 */
@Data
public class ReportData {
    private List<ReportItem> reportItems;
}
