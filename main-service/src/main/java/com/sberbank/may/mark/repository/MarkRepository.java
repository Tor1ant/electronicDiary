package com.sberbank.may.mark.repository;

import com.sberbank.may.mark.model.Mark;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с оценками.
 * Предоставляет функции для извлечения оценок студента определенных уроков и дат.
 */
@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    /**
     * Найти оценки студента для урока.
     *
     * @param lessonIds идентификаторы уроков.
     * @param studentId идентификатор студента.
     * @return оценки студента.
     */
    @Query(" select m from Mark as m "
            + "join m.lesson as l "
            + "join m.student as s "
            + "where l.id in(:lessonIds) and s.id = :studentId")
    List<Mark> findStudentMarksForLesson(@Param("lessonIds") List<Long> lessonIds, @Param("studentId") Long studentId);

    /**
     * Найти оценки студента для урока по определенному предмету в определенное время.
     *
     * @param predmetId идентификатор предмета.
     * @param studentId идентификатор студента.
     * @param from      начальная дата.
     * @param to        конечная дата.
     * @return оценки студента.
     */
    @Query(" select m from Mark as m "
            + "join m.lesson as l "
            + "join m.student as s "
            + "where l.predmet.id = :predmetId and s.id = :studentId "
            + " and l.lessonTime between :from and :to")
    List<Mark> findStudentMarksForLessonByPredmet(@Param("predmetId") Long predmetId,
            @Param("studentId") Long studentId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    /**
     * Найти оценки студента по предмету и датам.
     *
     * @param studentId идентификатор студента.
     * @param from      начальная дата.
     * @param to        конечная дата.
     * @param predmet   идентификатор предмета.
     * @return оценки студента.
     */
    @Query(" select m from Mark as m "
            + "join fetch m.lesson as l "
            + "join m.student as s "
            + "where s.id = :studentId and l.lessonTime between :from and :to "
            + "and l.predmet.id = :predmet")
    List<Mark> findStudentMarkByPredmetAndDates(@Param("studentId") Long studentId, @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to, @Param("predmet") Long predmet);

    /**
     * Искать оценки студентов на уроке.
     *
     * @param id идентификатор урока.
     * @return оценки студентов.
     */
    @Query("Select m from Mark m"
            + " where m.lesson.id =:lesson_id")
    List<Mark> searchStudentsMarksOnLesson(@Param("lesson_id") long id);
}
