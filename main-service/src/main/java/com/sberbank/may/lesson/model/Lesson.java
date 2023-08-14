package com.sberbank.may.lesson.model;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime lessonTime;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User teacher;
    @OneToOne
    private StudentClass studentClass;
    @OneToOne
    private Predmet predmet;
    @OneToOne
    private Homework homework;
}
