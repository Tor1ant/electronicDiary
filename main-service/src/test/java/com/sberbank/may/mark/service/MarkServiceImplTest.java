package com.sberbank.may.mark.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.student.dto.StudentWithMarkOut;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MarkServiceImplTest {

    @InjectMocks
    private MarkServiceImpl markService;
    @Mock
    private MarkRepository markRepository;

    private Lesson lesson1;
    private Mark mark1;
    private Student student1;

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
        StudentClass studentClass1 = new StudentClass();
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
        Student student2 = new Student();
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

    @DisplayName("Проверка создания оценки")
    @Test
    void saveMark() {
        when(markRepository.save(any())).thenReturn(mark1);
        markService.saveMark(mark1);
        Mockito.verify(markRepository).save(any());
    }

    @DisplayName("Проверка поиска студентов с оценками за урок")
    @Test
    void searchStudentsMarksOnLesson() {
        when(markRepository.searchStudentsMarksOnLesson(anyLong())).thenReturn(List.of(mark1));
        List<StudentWithMarkOut> studentWithMarkOuts = markService.searchStudentsMarksOnLesson(1L);
        assertThat(studentWithMarkOuts.get(0).getName(), is(student1.getName()));
        assertThat(studentWithMarkOuts.get(0).getMark(), is(5));
        verify(markRepository).searchStudentsMarksOnLesson(anyLong());
    }

    @DisplayName("Проверка удаления студентов по id")
    @Test
    void deleteById() {
        doNothing().when(markRepository).deleteById(anyLong());
        markService.deleteById(1L);
        verify(markRepository).deleteById(anyLong());
    }

    @DisplayName("Проверка поиска оценки по id")
    @Test
    void findMarkById() {
        when(markRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mark1));
        Mark markById = markService.findMarkById(1L);
        assertThat(markById, is(mark1));
        verify(markRepository).findById(anyLong());
    }

    @DisplayName("Проверка обновления оценки")
    @Test
    void patchMark() {
        Mark updatedMark = new Mark();
        updatedMark.setId(1L);
        updatedMark.setStudent(student1);
        updatedMark.setLesson(lesson1);
        updatedMark.setValue(4);
        when(markRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mark1));
        when(markRepository.save(any())).thenReturn(mark1);
        markService.patchMark(updatedMark);
        verify(markRepository).findById(anyLong());
        verify(markRepository).save(any());
    }
}