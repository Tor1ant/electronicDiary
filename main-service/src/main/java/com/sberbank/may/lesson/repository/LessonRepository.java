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

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("Select l from Lesson l " +
            "where (:teacherName = '' or l.teacher.name = :teacherName)" +
            " and (:className = '' or l.studentClass.title = :className)" +
            " and (:subjectName = '' or l.predmet.title = :subjectName)" +
            " and (l.lessonTime = :lessonTime or l.lessonTime < :lessonTime)")
    Optional<List<Lesson>> searchLesson(@Param("teacherName") String teacherName,
            @Param("className") String className,
            @Param("subjectName") String subjectName,
            @Param("lessonTime") LocalDateTime lessonTime);

    @Query("select l from Lesson l join l.studentClass.students s where s.id = :studentId and "
            + " DATE(l.lessonTime) = :lessonDate")
    Optional<List<Lesson>> getStudentSchedule(@Param("studentId") Long studentId,
            @Param("lessonDate") LocalDate lessonDate);

    @Query("SELECT l.id FROM Lesson l")
    List<Long> findLessonIdsForYear();


}
