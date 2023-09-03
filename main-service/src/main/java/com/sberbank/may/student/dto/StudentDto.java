package com.sberbank.may.student.dto;

import com.sberbank.may.studentClass.model.StudentClass;
import lombok.Data;

/**
 * Класс StudentDto служит для передачи данных между слоями приложения.
 **/
@Data
public class StudentDto {

    private String name;
    private StudentClass studentClass;
}
