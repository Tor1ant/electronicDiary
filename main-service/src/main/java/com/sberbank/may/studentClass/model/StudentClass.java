package com.sberbank.may.studentClass.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_classes")
@Data
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
}
