package com.sberbank.may.lesson.repository;

import com.sberbank.may.lesson.model.Lesson;

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

    @Query("Select l from Lesson l " +
            "where (l.teacher.name = :teacherName)" +
            " and (l.lessonTime >= :lessonTimeFrom and l.lessonTime <= :lessonTimeTo)")
    Optional<List<Lesson>> searchAllLessonsForTeacher(@Param("teacherName") String teacherName,
                                                @Param("lessonTimeFrom") LocalDateTime lessonTimeFrom,
                                                @Param("lessonTimeTo") LocalDateTime lessonTimeTo);
}
