package com.sberbank.may.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс представляет отчетную единицу.
 * <p>
 * Этот класс представляет собой отчетную единицу с полями: firstName, averageGrade и predmet.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportItem {
    private String firstName;
    private String averageGrade;
    private String predmet;
}
