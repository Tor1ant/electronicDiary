package com.sberbank.may.mark.model;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.student.model.Student;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Класс сущности Mark, представляющий оценку учащегося за урок.
 * Объект этого класса отображается на запись таблицы 'student_lesson' в базе данных.
 * На каждую запись в таблице 'student_lesson' приходится одна оценка учащегося за урок.
 */
@Entity
@Table(name = "student_lesson")
@Data
public class Mark {
    /**
     * Уникальный идентификатор оценки (ключ в таблице 'student_lesson').
     * Стратегия генерации - IDENTITY, ID будет генерироваться автоматически в базе данных при сохранении объекта.
     */
    @Id
    @Column(name = "id_mark")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Значение оценки учащегося за урок.
     */
    private int value;

    /**
     * Урок, на котором была получена оценка.
     * Используется для связи многих экземпляров класса Mark к одному экземпляру класса Lesson (Many-to-One).
     */
    @ManyToOne
    private Lesson lesson;

    /**
     * Учащийся, которому была выставлена оценка.
     * Используется для связи многих экземпляров класса Mark к одному экземпляру класса Student (Many-to-One).
     */
    @ManyToOne
    private Student student;
}
