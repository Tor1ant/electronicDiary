package com.sberbank.may.student.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportData {
    private List<ReportItem> reportItems;
}
