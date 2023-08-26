package com.sberbank.may.student.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final MarkRepository markRepository;

    @Transactional
    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> searchStudent(StudentDto studentDto) {
        return studentRepository.findStudentByNameLikeIgnoreCaseAndStudentClass(studentDto.getName(),
                studentDto.getStudentClass());
    }

    @Transactional
    @Override
    public List<Student> searchAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    @Override
    public Student findStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ученик с id=" + id + " не найден"));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void patchStudent(Student student) {
        Student studentForUpdate = findStudentById(student.getId());
        studentForUpdate.setName(student.getName());
        studentForUpdate.setStudentClass(student.getStudentClass());
        studentForUpdate.setUser(student.getUser());
        studentRepository.save(studentForUpdate);
    }

    @Override
    public List<Student> searchAllStudentsOnLesson(long id) {
        return studentRepository.searchAllStudentsOnLesson(id)
                .orElseThrow(() -> new NotFoundException("Ученики с id урока = " + id + " не найдены"));
    }

    @Override
    public List<LessonWithMarkOut> getStudentSchedule(Long studentId, LocalDate date) {
        Map<Long, LessonWithMarkOut> lessonWithMarkOuts = lessonRepository.getStudentSchedule(studentId, date)
                .orElseThrow(() -> new NotFoundException("По заданным параметрам расписание не найдено")).stream()
                .map(lesson -> LessonWithMarkOut.builder()
                        .studentClass(lesson.getStudentClass())
                        .lessonTime(lesson.getLessonTime())
                        .teacher(lesson.getTeacher())
                        .homework(lesson.getHomework())
                        .predmet(lesson.getPredmet())
                        .id(lesson.getId())
                        .build())
                .collect(Collectors.toMap(LessonWithMarkOut::getId, lessonWithMarkOut -> lessonWithMarkOut));

        markRepository.findStudentMarksForLesson(lessonWithMarkOuts.values().stream()
                        .map(LessonWithMarkOut::getId)
                        .collect(Collectors.toList()), studentId)
                .forEach(mark -> lessonWithMarkOuts.get(mark.getLesson().getId()).setMark(mark.getValue()));

        return new ArrayList<>(lessonWithMarkOuts.values());
    }

    @Override
    public List<LessonWithMarkOut> getStudentMarks(Long studentId, Long predmetId) {
        return markRepository.findStudentMarksForLessonByPredmet(studentId,
                        predmetId).stream()
                .map(mark -> LessonWithMarkOut.builder()
                        .studentClass(mark.getLesson().getStudentClass())
                        .lessonTime(mark.getLesson().getLessonTime())
                        .teacher(mark.getLesson().getTeacher())
                        .homework(mark.getLesson().getHomework())
                        .predmet(mark.getLesson().getPredmet())
                        .id(mark.getId())
                        .mark(mark.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}

