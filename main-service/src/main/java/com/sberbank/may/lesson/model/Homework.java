package com.sberbank.may.lesson.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Это представление сущности "Homework". Сущность включает в себя несколько полей и аннотирована для работы с базой
 * данных. Она аннотирована как @Entity, что указывает на то, что класс должен быть сопоставлен с таблицей базы данных.
 * Таблица базы данных сопоставлена с помощью аннотации @Table.
 */
@Entity
@Table(name = "homeworks")
@Data
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
}
