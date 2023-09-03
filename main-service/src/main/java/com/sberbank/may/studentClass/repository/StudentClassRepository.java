package com.sberbank.may.studentClass.repository;

import com.sberbank.may.studentClass.model.StudentClass;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью StudentClass.
 * Расширяет JpaRepository для использования стандартных операций CRUD.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

    /**
     * Поиск класса студента по названию.
     *
     * @param title Название класса студента.
     * @return Опциональый объект класса студента, который соответствует указанному названию.
     */
    Optional<StudentClass> findByTitle(String title);

    /**
     * Поиск всех классов студентов, название которых содержит указанную строку,
     * бессознательно к регистру символов.
     *
     * @param title Строка для поиска в названии класса студента.
     * @return Список классов студентов, которые содержат указанную строку в своем названии.
     */
    List<StudentClass> findStudentClassByTitleLikeIgnoreCase(String title);
}
