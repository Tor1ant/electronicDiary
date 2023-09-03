package com.sberbank.may.studentClass.model;

import com.sberbank.may.student.model.Student;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;

/**
 * Класс представляет объектное представление студентского класса в базе данных.
 * Аннотация @Entity используется для указания того, что данный класс является сущностью.
 * Аннотация @Table(name = "student_classes") указывает на имя таблицы в базе данных, которую представляет этот класс.
 * Аннотация @Data от Lombok генерирует базовые функции, такие как getters, setters и toString, автоматически.
 */
@Entity
@Table(name = "student_classes")
@Data
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @OneToMany(mappedBy = "studentClass")
    private Set<Student> students;
}
