package com.sberbank.may.student.dto;

import com.sberbank.may.studentClass.model.StudentClass;
import lombok.Data;

@Data
public class StudentDto {
    private String name;
    private StudentClass studentClass;
}
