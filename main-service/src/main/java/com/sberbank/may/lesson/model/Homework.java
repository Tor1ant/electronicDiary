package com.sberbank.may.lesson.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "homeworks")
@Data
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
}
