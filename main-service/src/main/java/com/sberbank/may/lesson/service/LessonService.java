package com.sberbank.may.lesson.service;

import com.sberbank.may.lesson.model.Lesson;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {

    void createLesson(Lesson lesson);

    List<Lesson> searchLessons(String teacherName, String className, String subjectName, LocalDateTime lessonTime);

    void deleteById(long id);

    Lesson findLessonById(long id);

    void updateLesson(Lesson lesson);

    List<Lesson> getAllLessons();

    List<Lesson> searchLessonsForTeacher(String teacherName, String className, String subjectName,
                                         LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo);

    List<Lesson> searchLessonsForTeacher(String teacherName, LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo);
}
