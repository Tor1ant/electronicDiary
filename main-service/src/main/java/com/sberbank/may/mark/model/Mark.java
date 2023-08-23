package com.sberbank.may.mark.model;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.student.model.Student;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_lesson")
@Data
public class Mark {
    @Id
    @Column(name = "id_mark")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int value;
    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private Student student;
}
