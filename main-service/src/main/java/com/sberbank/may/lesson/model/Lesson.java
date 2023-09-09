package com.sberbank.may.lesson.model;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Класс сущности, представляющий урок в учебной системе.
 * Представляет собой таблицу "lessons" в базе данных.
 */
@Entity
@Table(name = "lessons")
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime lessonTime;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private User teacher;
    @OneToOne(cascade = CascadeType.MERGE)
    private StudentClass studentClass;
    @OneToOne(cascade = CascadeType.MERGE)
    private Predmet predmet;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "homework_id")
    private Homework homework;
}
