package com.sberbank.may.studentClass.model;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.student.model.Student;
import jakarta.persistence.*;
import java.util.List;
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
