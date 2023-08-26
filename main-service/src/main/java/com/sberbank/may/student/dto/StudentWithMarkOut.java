package com.sberbank.may.student.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentWithMarkOut {
    private Long id;
    private String name;
    private Integer mark;
}
