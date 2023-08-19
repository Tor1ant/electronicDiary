package com.sberbank.may.mark.model;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.student.model.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "mark_lesson_student")
@Data
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int value;
    @ManyToMany
    private List<Lesson> lessons;
    @ManyToMany(mappedBy = "marks")
    private List<Student> students;
}
