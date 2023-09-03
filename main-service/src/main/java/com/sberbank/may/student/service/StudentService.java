package com.sberbank.may.student.service;

import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс службы для управления студентами
 */
public interface StudentService {

    /**
     * Сохраняет информацию о студенте
     *
     * @param student информация о студенте
     */
    void saveStudent(Student student);

    /**
     * Выполняет поиск списка студентов по параметрам
     *
     * @param studentDto DTO объект, содержащий параметры для поиска
     * @return список студентов
     */
    List<Student> searchStudent(StudentDto studentDto);

    /**
     * Возвращает список всех студентов
     *
     * @return список всех студентов
     */
    List<Student> searchAllStudents();

    /**
     * Выполняет поиск студента по id
     *
     * @param id id студента
     * @return студента
     */
    Student findStudentById(long id);

    /**
     * Удаляет студента по id
     *
     * @param id id студента
     */
    void deleteById(long id);

    /**
     * Обновляет информацию о студенте
     *
     * @param student объект студента с новой информацией
     */
    void patchStudent(Student student);

    /**
     * Выполняет поиск всех студентов, присутствующих на уроке
     *
     * @param id id урока
     * @return список студентов
     */
    List<Student> searchAllStudentsOnLesson(long id);

    /**
     * Возвращает расписание студента за определенную дату
     *
     * @param studentId id студента
     * @param date      дата, на которую нужно получить расписание
     * @return список уроков со сведениями о них
     */
    List<LessonWithMarkOut> getStudentSchedule(Long studentId, LocalDate date);

    /**
     * Возвращает все оценки определенного студента по определенному предмету в определенный временной интервал.
     *
     * @param studentId      id студента
     * @param predmetId      id предмета
     * @param lessonTimeFrom начало временного периода
     * @param lessonTimeTo   конец временного периода
     * @return список уроков со сведениями об оценках
     */
    List<LessonWithMarkOut> getStudentMarks(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom,
            LocalDateTime lessonTimeTo);

    /**
     * Возвращает среднюю оценку студента по определенному предмету в определенный временной период.
     *
     * @param studentId      id студента
     * @param predmetId      id предмета
     * @param lessonTimeFrom начало временного периода
     * @param lessonTimeTo   конец временного периода
     * @return отчет о средней оценке студента
     */
    ResponseEntity<byte[]> getAvgMarkReport(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom,
            LocalDateTime lessonTimeTo);
}
