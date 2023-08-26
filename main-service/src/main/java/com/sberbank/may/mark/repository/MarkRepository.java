package com.sberbank.may.mark.repository;

import com.sberbank.may.mark.model.Mark;
import java.util.List;

import com.sberbank.may.student.dto.StudentWithMarkOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    @Query(" select m from Mark as m "
            + "join m.lesson as l "
            + "join m.student as s "
            + "where l.id in(:lessonIds) and s.id = :studentId")
    List<Mark> findStudentMarksForLesson(@Param("lessonIds") List<Long> lessonIds, @Param("studentId") Long studentId);

    @Query(" select m from Mark as m "
            + "join m.lesson as l "
            + "join m.student as s "
            + "where l.predmet.id = :predmetId and s.id = :studentId")
    List<Mark> findStudentMarksForLessonByPredmet(@Param("predmetId") Long predmetId,
            @Param("studentId") Long studentId);

    @Query("Select m from Mark m"
            + " where m.lesson.id =:lesson_id")
    List<Mark> searchStudentsMarksOnLesson(@Param("lesson_id") long id);
}
