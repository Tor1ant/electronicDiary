package com.sberbank.may.lesson.service;

import com.sberbank.may.lesson.model.Lesson;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы со занятиями.
 */
public interface LessonService {

    /**
     * Создает новое занятие.
     *
     * @param lesson занятие, которое необходимо создать
     */
    void createLesson(Lesson lesson);

    /**
     * Предоставляет поиск занятий по имени преподавателя, названию класса, названию предмета и времени занятия.
     *
     * @param teacherName имя преподавателя
     * @param className   название класса
     * @param subjectName название предмета
     * @param lessonTime  время занятия
     * @return список занятий, удовлетворяющих критериям поиска
     */
    List<Lesson> searchLessons(String teacherName, String className, String subjectName, LocalDateTime lessonTime);

    /**
     * Удаляет занятие по-указанному ID.
     *
     * @param id уникальный идентификационный номер занятия
     */
    void deleteById(long id);

    /**
     * Возвращает занятие по-указанному ID.
     *
     * @param id уникальный идентификационный номер занятия
     * @return занятие с указанным ID
     */
    Lesson findLessonById(long id);

    /**
     * Обновляет информацию о занятии.
     *
     * @param lesson занятие с обновленной информацией
     */
    void updateLesson(Lesson lesson);

    /**
     * Получает все занятия.
     *
     * @return общий список занятий
     */
    List<Lesson> getAllLessons();

    /**
     * Поиск занятий для преподавателя за указанный период времени.
     *
     * @param teacherName    имя преподавателя
     * @param className      название класса
     * @param subjectName    название предмета
     * @param lessonTimeFrom начальное время для отбора занятий
     * @param lessonTimeTo   конечное время для отбора занятий
     * @return список занятий, удовлетворяющих критериям поиска
     */

    List<Lesson> searchLessonsForTeacher(String teacherName, String className, String subjectName,
            LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo);

    /**
     * Поиск занятий для преподавателя за указанный период времени, независимо от предмета и класса.
     *
     * @param teacherName    имя преподавателя
     * @param lessonTimeFrom начальное время для отбора занятий
     * @param lessonTimeTo   конечное время для отбора занятий
     * @return список занятий, удовлетворяющих критериям поиска
     */
    List<Lesson> searchLessonsForTeacher(String teacherName, LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo);
}
