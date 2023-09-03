package com.sberbank.may.mark.service;

import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.student.dto.StudentWithMarkOut;

import java.util.List;

/**
 * Интерфейс содержит общий функционал сервиса для оценок.
 */
public interface MarkService {

    /**
     * Сохраняет объект Mark.
     *
     * @param mark объект Mark для сохранения
     */
    void saveMark(Mark mark);

    /**
     * Ищет всех студентов и их оценки по id урока.
     *
     * @param id идентификатор урока
     * @return Список объектов StudentWithMarkOut, содержащих информацию о студенте и его оценках
     */
    List<StudentWithMarkOut> searchStudentsMarksOnLesson(long id);

    /**
     * Удаляет оценку по id.
     *
     * @param id идентификатор оценки для удаления
     */
    void deleteById(long id);

    /**
     * Находит Mark по id.
     *
     * @param id идентификатор Mark
     * @return Mark, найденный по id
     */
    Mark findMarkById(long id);

    /**
     * Применяет Patch к объекту Mark.
     *
     * @param mark объект Mark для применения patch
     */
    void patchMark(Mark mark);
}
