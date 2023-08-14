package com.sberbank.may.student.model;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //продумать связь many to many
    @ManyToOne
    private User user;

    @ManyToMany
    private List<Lesson> lessons;

    @ManyToMany
    private List<Mark> marks;

}
