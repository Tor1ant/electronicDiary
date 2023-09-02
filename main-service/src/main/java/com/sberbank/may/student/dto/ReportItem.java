package com.sberbank.may.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem {
    private String firstName;
    private String averageGrade;
    private String predmet;
}
