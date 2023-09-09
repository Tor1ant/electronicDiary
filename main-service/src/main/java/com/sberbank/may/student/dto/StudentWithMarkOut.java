package com.sberbank.may.student.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Класс представления данных для студента с оценкой.
 */
@Data
@Builder
public class StudentWithMarkOut {
    private Long id;
    private String name;
    private Integer mark;
}
