package com.sberbank.may.studentClass.dto;

import lombok.Data;

/**
 * Данный класс представляет собой DTO (Data Transfer Object) для класса студента. Это простой класс с полями и
 * функциями для их чтения и записи, который используется для передачи данных.
 * <p>
 * Аннотация @Data является частью библиотеки Lombok и автоматически добавляет геттеры, сеттеры и другие полезные
 * функции.
 */
@Data
public class StudentClassDto {

    private String title;
}
