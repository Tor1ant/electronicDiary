package com.sberbank.may.student.service;

import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    void saveStudent(Student student);

    List<Student> searchStudent(StudentDto studentDto);

    List<Student> searchAllStudents();

    Student findStudentById(long id);

    void deleteById(long id);

    void patchStudent(Student student);

    List<Student> searchAllStudentsOnLesson(long id);

    List<LessonWithMarkOut> getStudentSchedule(Long studentId, LocalDate date);

    List<LessonWithMarkOut> getStudentMarks(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom,
            LocalDateTime lessonTimeTo);

    Mono<Double> getAvgMarkReport(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo);
}
