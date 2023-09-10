package com.sberbank.may.lesson.repository;

import com.sberbank.may.lesson.model.Lesson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Урок (Lesson)
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Метод для поиска уроков по заданным параметрам.
     *
     * @param teacherName имя преподавателя
     * @param className имя класса
     * @param subjectName название предмета
     * @param lessonTime время урока
     * @return список уроков, соответствующих заданным параметрам
     */
    @Query("Select l from Lesson l " +
            "where (:teacherName = '' or l.teacher.name = :teacherName)" +
            " and (:className = '' or l.studentClass.title = :className)" +
            " and (:subjectName = '' or l.predmet.title = :subjectName)" +
            " and (l.lessonTime = :lessonTime or l.lessonTime < :lessonTime)")
    Optional<List<Lesson>> searchLesson(@Param("teacherName") String teacherName,
                                        @Param("className") String className,
                                        @Param("subjectName") String subjectName,
                                        @Param("lessonTime") LocalDateTime lessonTime);

    /**
     * Метод для поиска уроков преподавателя в заданном промежутке времени.
     *
     * @param teacherName имя преподавателя
     * @param className имя класса
     * @param subjectName название предмета
     * @param lessonTimeFrom начало промежутка времени
     * @param lessonTimeTo конец промежутка времени
     * @return список уроков, соответствующих заданным параметрам
     */
    @Query("Select l from Lesson l " +
            "where (l.teacher.name = :teacherName)" +
            " and (l.studentClass.title = :className)" +
            " and (l.predmet.title = :subjectName)" +
            " and (l.lessonTime >= :lessonTimeFrom and l.lessonTime <= :lessonTimeTo)")
    Optional<List<Lesson>> searchLessonsForTeacher(@Param("teacherName") String teacherName,
                                                   @Param("className") String className,
                                                   @Param("subjectName") String subjectName,
                                                   @Param("lessonTimeFrom") LocalDateTime lessonTimeFrom,
                                                   @Param("lessonTimeTo") LocalDateTime lessonTimeTo);

    /**
     * Метод для поиска всех уроков преподавателя в заданном промежутке времени.
     *
     * @param teacherName имя преподавателя
     * @param lessonTimeFrom начало промежутка времени
     * @param lessonTimeTo конец промежутка времени
     * @return список уроков, соответствующих заданным параметрам
     */
    @Query("Select l from Lesson l " +
            "where (l.teacher.name = :teacherName)" +
            " and (l.lessonTime >= :lessonTimeFrom and l.lessonTime <= :lessonTimeTo)")
    Optional<List<Lesson>> searchAllLessonsForTeacher(@Param("teacherName") String teacherName,
                                                @Param("lessonTimeFrom") LocalDateTime lessonTimeFrom,
                                                @Param("lessonTimeTo") LocalDateTime lessonTimeTo);

    /**
     * Метод для получения расписания ученика на заданную дату.
     *
     * @param studentId ID ученика
     * @param lessonDate дата урока
     * @return список уроков, соответствующих заданным параметрам
     */
    @Query("select l from Lesson l join l.studentClass.students s where s.id = :studentId and "
            + " DATE(l.lessonTime) = :lessonDate")
    Optional<List<Lesson>> getStudentSchedule(@Param("studentId") Long studentId,
            @Param("lessonDate") LocalDate lessonDate);
}
