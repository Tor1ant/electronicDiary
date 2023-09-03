package com.sberbank.may.predmet.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Класс сущности "Предмет" для отображения на соответствующую таблицу в базе данных.
 */
@Entity
@Table(name = "predmets")
@Data
public class Predmet {

    /**
     * Идентификатор предмета. Это первичный ключ, который генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название предмета.
     */
    private String title;
}
