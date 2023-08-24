package com.sberbank.may.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem {
    private String lastName;
    private String firstName;
    private String averageGrade;
}
