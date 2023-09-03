package com.sberbank.may.lesson.repository;

import com.sberbank.may.lesson.model.Homework;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с Домашними заданиями в базе данных. Наследуется от JpaRepository, поэтому оно будет
 * автоматически реализовано Spring'ом. Это позволяет использовать огромное количество методов для работы с базой данных
 * без их реализации.
 */
@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    /**
     * Ищет домашнее задание в базе данных по его описанию.
     *
     * @param description описание домашнего задания, которое нужно найти.
     * @return Optional, который содержит найденное домашнее задание или пусто, если задание с данным описанием не
     * найдено.
     */
    Optional<Homework> findByDescription(String description);
}
