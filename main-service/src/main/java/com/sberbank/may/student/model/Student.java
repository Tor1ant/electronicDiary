package com.sberbank.may.student.model;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.model.User;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;

import java.util.List;

/**
 * Класс представляет собой сущность "Student" в БД.
 * Используется аннотация @Data для автоматической генерации геттеров и сеттеров.
 * Entity аннотация указывает, что данный класс является сущностью.
 * Table(name = "students") аннотация указывает, что этой сущности в БД соответствует таблица "students".
 */
@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_user",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_lesson",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id")}
    )
    private List<Lesson> markLessons;

    @ManyToOne
    private StudentClass studentClass;
}
