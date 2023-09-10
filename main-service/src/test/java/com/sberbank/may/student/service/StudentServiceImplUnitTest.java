package com.sberbank.may.student.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.student.controller.StudentController;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplUnitTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private MarkRepository markRepository;


    private Student student1;
    private Student student2;
    private Mark mark1;
    private StudentClass studentClass1;
    private Lesson lesson1;

    @BeforeEach
    void setUp() {
        User teacher1 = new User(1L, "Румянцева Анастасия Ивановна", "password",
                "testMail@mail.ru", "+79211554785",
                Role.ROLE_TEACHER);
        User teacher2 = new User(2L, "Павлов Михаил Фёдорович", "password222",
                "Mihail@google.com", "+79211554785",
                Role.ROLE_TEACHER);
        User parent1 = new User(4L, " Чехов Антон Павлович", "мертвыедуши",
                "goldenAge@mail.ru", "+79214568752",
                Role.ROLE_PARENT);
        User parent2 = new User(5L, "Расторгуева Мария Александровна", "zdanie255",
                "MAR@mail.ru", "+7523654789",
                Role.ROLE_PARENT);
        User parent3 = new User(6L, "Расторгуев Павел Владимирович", "1998",
                "PVR@mail.ru", "+79635284512",
                Role.ROLE_PARENT);
        Predmet subject1 = new Predmet();
        subject1.setId(1L);
        subject1.setTitle("Математика");
        Predmet subject2 = new Predmet();
        subject1.setId(2L);
        subject2.setTitle("Русский язык");
        studentClass1 = new StudentClass();
        studentClass1.setId(1L);
        studentClass1.setTitle("5А");
        StudentClass studentClass2 = new StudentClass();
        studentClass2.setId(2L);
        studentClass2.setTitle("11Б");
        student1 = new Student();
        student1.setId(1L);
        student1.setName("Расторгуев Максим Павлович");
        student1.setUser(Set.of(parent2, parent3));
        student1.setStudentClass(studentClass1);
        student2 = new Student();
        student2.setId(2L);
        student2.setName("Чехов Михаил Антонович");
        student2.setUser(Set.of(parent1));
        student2.setStudentClass(studentClass2);
        Homework homework1 = new Homework();
        homework1.setId(1L);
        homework1.setDescription("Сделать что-нибудь");
        lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setLessonTime(LocalDateTime.now());
        lesson1.setHomework(homework1);
        lesson1.setTeacher(teacher1);
        lesson1.setPredmet(subject1);
        lesson1.setStudentClass(studentClass1);
        Lesson lesson2 = new Lesson();
        lesson2.setStudentClass(studentClass2);
        lesson2.setId(2L);
        lesson2.setLessonTime(LocalDateTime.now().plusDays(1));
        lesson2.setTeacher(teacher2);
        lesson2.setHomework(homework1);
        lesson2.setPredmet(subject2);
        mark1 = new Mark();
        mark1.setLesson(lesson1);
        mark1.setStudent(student1);
        mark1.setValue(5);
        mark1.setId(1L);
    }

    @DisplayName("Проверка создания ученика")
    @Test
    void testSaveStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        studentService.saveStudent(student1);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @DisplayName("Проверка поиска ученика")
    @Test
    void testSearchStudent() {
        when(studentRepository.findStudentByNameLikeIgnoreCaseAndStudentClass(anyString(), any(StudentClass.class)))
                .thenReturn(List.of(student1));
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentClass(student1.getStudentClass());
        studentDto.setName(student1.getName());
        List<Student> students = studentService.searchStudent(studentDto);
        assertThat(students, contains(student1));
        verify(studentRepository).findStudentByNameLikeIgnoreCaseAndStudentClass(anyString(), any(StudentClass.class));
    }

    @DisplayName("Проверка получения всех учеников")
    @Test
    void testSearchAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));
        List<Student> allStudents = studentService.searchAllStudents();
        assertThat(allStudents, contains(student1, student2));
        verify(studentRepository).findAll();
    }

    @DisplayName("Проверка поиска ученика по id")
    @Test
    void testFindStudentById() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student1));
        Student studentById = studentService.findStudentById(1L);
        assertThat(student1, is(studentById));
        verify(studentRepository).findById(anyLong());
    }

    @DisplayName("Проверка удаления ученика по id")
    @Test
    void testDeleteById() {
        doNothing().when(studentRepository).deleteById(anyLong());
        studentService.deleteById(1L);
        verify(studentRepository).deleteById(anyLong());
    }

    @DisplayName("Проверка обновления ученика")
    @Test
    void patchStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student1));
        studentService.patchStudent(student1);
        verify(studentRepository).save(any());
    }

    @DisplayName("Проверка поиска учеников на уроке")
    @Test
    void testSearchAllStudentsOnLesson() {
        when(studentRepository.searchAllStudentsOnLesson(anyLong())).thenReturn(Optional.of(List.of(student1)));
        List<Student> students = studentService.searchAllStudentsOnLesson(1L);
        assertThat(students, contains(student1));
        verify(studentRepository).searchAllStudentsOnLesson(anyLong());
    }

    @DisplayName("Проверка поиска учеников на уроке. Ученики не найдены")
    @Test
    void testSearchAllStudentsOnLessonNotFound() {
        when(studentRepository.searchAllStudentsOnLesson(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> studentService.searchAllStudentsOnLesson(1L));
        verify(studentRepository).searchAllStudentsOnLesson(anyLong());
    }

    @DisplayName("Проверка получения расписания ученика")
    @Test
    void testGetStudentSchedule() {
        when(lessonRepository.getStudentSchedule(anyLong(), any())).thenReturn(Optional.of(List.of(lesson1)));
        when(markRepository.findStudentMarksForLesson(anyList(), anyLong())).thenReturn(List.of(mark1));
        List<LessonWithMarkOut> lessonWithMarkOuts = studentService.getStudentSchedule(1L, LocalDate.now());
        LessonWithMarkOut lessonWithMarkOut = LessonWithMarkOut.builder()
                .studentClass(studentClass1)
                .lessonTime(lesson1.getLessonTime())
                .teacher(lesson1.getTeacher())
                .homework(lesson1.getHomework())
                .predmet(lesson1.getPredmet())
                .id(lesson1.getId())
                .mark(mark1.getValue())
                .build();
        List<LessonWithMarkOut> expectedLessonWithMarkOuts = List.of(lessonWithMarkOut);
        assertThat(lessonWithMarkOuts, is(expectedLessonWithMarkOuts));
        verify(lessonRepository).getStudentSchedule(anyLong(), any());
        verify(markRepository).findStudentMarksForLesson(anyList(), anyLong());
    }

    @DisplayName("Проверка получения оценок ученика по предмету")
    @Test
    void testGetStudentMarks() {
        when(markRepository.findStudentMarksForLessonByPredmet(anyLong(), anyLong(), any(), any())).thenReturn(
                List.of(mark1));
        List<LessonWithMarkOut> lessonWithMarkOuts = studentService.getStudentMarks(1L, 1L,
                LocalDateTime.now(), LocalDateTime.now());
        LessonWithMarkOut lessonWithMarkOut = LessonWithMarkOut.builder()
                .studentClass(studentClass1)
                .lessonTime(lesson1.getLessonTime())
                .teacher(lesson1.getTeacher())
                .homework(lesson1.getHomework())
                .predmet(lesson1.getPredmet())
                .id(lesson1.getId())
                .mark(mark1.getValue())
                .build();
        List<LessonWithMarkOut> expectedLessonWithMarkOuts = List.of(lessonWithMarkOut);
        assertThat(lessonWithMarkOuts, is(expectedLessonWithMarkOuts));
        verify(markRepository).findStudentMarksForLessonByPredmet(anyLong(), anyLong(), any(), any());
    }

//    @Ignore
//    @Test
//    void testGetAvgMarkReport() {
//        Long studentId = 1L;
//        Long predmetId = 1L;
//        LocalDateTime lessonTimeFrom = LocalDateTime.now().minusDays(7);
//        LocalDateTime lessonTimeTo = LocalDateTime.now();
//
//        List<Student> students = new ArrayList<>();
//        Student student = new Student();
//        student.setId(studentId);
//        student.setName("John");
//        students.add(student);
//        when(studentRepository.searchAllStudentsOnLesson(predmetId)).thenReturn(Optional.of(students));
//
//        List<Mark> studentMarks = new ArrayList<>();
//        Mark mark = new Mark();
//        mark.setStudent(student);
//        mark.setValue(85);
//        studentMarks.add(mark);
//        when(markRepository.findStudentMarkByPredmetAndDates(studentId, lessonTimeFrom, lessonTimeTo, predmetId))
//                .thenReturn(studentMarks);
//
//        StudentServiceImpl service = new StudentServiceImpl(studentRepository, lessonRepository, markRepository);
//        ResponseEntity<byte[]> response = service.getAvgMarkReport(studentId, predmetId, lessonTimeFrom, lessonTimeTo);
//
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue());
//
//
//        verify(studentRepository, times(1)).searchAllStudentsOnLesson(predmetId);
//        verify(markRepository, times(1)).findStudentMarkByPredmetAndDates(studentId, lessonTimeFrom, lessonTimeTo, predmetId);
//    }



}