package com.sberbank.may.dto;

import com.sberbank.may.dto.ReportItem;
import lombok.Data;

import java.util.List;

@Data
public class ReportData {
    private List<ReportItem> reportItems;
}
