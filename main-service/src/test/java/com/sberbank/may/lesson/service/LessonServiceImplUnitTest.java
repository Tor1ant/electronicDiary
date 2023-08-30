package com.sberbank.may.lesson.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.HomeworkRepository;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplUnitTest {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StudentClassRepository studentClassRepository;
    @Mock
    private PredmetRepository predmetRepository;
    @Mock
    private HomeworkRepository homeworkRepository;

    private User teacher1;
    private Predmet subject1;
    private StudentClass studentClass1;
    private Homework homework1;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    void setUp() {
        teacher1 = new User(1L, "Румянцева Анастасия Ивановна", "password",
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
        subject1 = new Predmet();
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
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Расторгуев Максим Павлович");
        student1.setUser(Set.of(parent2, parent3));
        student1.setStudentClass(studentClass1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Чехов Михаил Антонович");
        student2.setUser(Set.of(parent1));
        student2.setStudentClass(studentClass2);
        homework1 = new Homework();
        homework1.setId(1L);
        homework1.setDescription("Сделать что-нибудь");
        lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setLessonTime(LocalDateTime.now());
        lesson1.setHomework(homework1);
        lesson1.setTeacher(teacher1);
        lesson1.setPredmet(subject1);
        lesson1.setStudentClass(studentClass1);
        lesson2 = new Lesson();
        lesson2.setStudentClass(studentClass2);
        lesson2.setId(2L);
        lesson2.setLessonTime(LocalDateTime.now().plusDays(1));
        lesson2.setTeacher(teacher2);
        lesson2.setHomework(homework1);
        lesson2.setPredmet(subject2);
        Mark mark1 = new Mark();
        mark1.setLesson(lesson1);
        mark1.setStudent(student1);
        mark1.setValue(5);
        mark1.setId(1L);
    }

    @DisplayName("Проверка создания урока")
    @Test
    void createLesson() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        when(predmetRepository.findById(anyLong())).thenReturn(Optional.of(subject1));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson1);
        lessonService.createLesson(lesson1);
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @DisplayName("Проверка создания урока с несуществующим пользователем")
    @Test
    void createLessonWithNotFoundUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.createLesson(lesson1));
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @DisplayName("Проверка создания урока с несуществующим классом студентов")
    @Test
    void createLessonWithNotFoundStudentClass() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.createLesson(lesson1));
        verify(userRepository).findById(anyLong());
        verify(studentClassRepository).findById(anyLong());
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @DisplayName("Проверка создания урока с несуществующим предметом")
    @Test
    void createLessonWithNotFoundPredmet() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        when(predmetRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.createLesson(lesson1));
        verify(lessonRepository, times(0)).save(any(Lesson.class));
    }

    @DisplayName("Проверка создания урока с пустым домашним заданием")
    @Test
    void createLessonWithEmptyHomework() {
        homework1.setDescription("");
        lesson1.setHomework(homework1);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(teacher1));
        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass1));
        when(predmetRepository.findById(anyLong())).thenReturn(Optional.of(subject1));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson1);
        lessonService.createLesson(lesson1);
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @DisplayName("Проверка поиска урока по параметрам")
    @Test
    void searchLessons() {
        when(lessonRepository.searchLesson(anyString(), anyString(), anyString(), any()))
                .thenReturn(Optional.of(List.of(lesson2)));
        List<Lesson> lessons = lessonService.searchLessons("ff", "dd", "sda",
                LocalDateTime.now());
        assertThat(lessons, contains(lesson2));
        verify(lessonRepository).searchLesson(anyString(), anyString(), anyString(), any());
    }

    @DisplayName("Проверка удаления урока по id")
    @Test
    void deleteById() {
        doNothing().when(lessonRepository).deleteById(anyLong());
        lessonService.deleteById(1L);
        verify(lessonRepository).deleteById(anyLong());
    }

    @DisplayName("Проверка поиска урока по id")
    @Test
    void findLessonById() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson2));
        Lesson lessonById = lessonService.findLessonById(2L);
        assertThat(lesson2, is(lessonById));
        verify(lessonRepository).findById(anyLong());
    }

    @DisplayName("Проверка обновления урока")
    @Test
    void updateLesson() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson1));
        lessonService.updateLesson(lesson1);
        verify(userRepository, times(0)).findUserByName(anyString());
        verify(studentClassRepository, times(0)).findByTitle(anyString());
        verify(predmetRepository, times(0)).findByTitle(anyString());
        verify(homeworkRepository, times(0)).findByDescription(anyString());
        verify(lessonRepository).save(any());
    }

    @DisplayName("Проверка обновления урока с несуществующим учителем")
    @Test
    void updateLessonWithNotFoundTeacher() {
        Lesson lessonWithNotFoundTeacher = new Lesson();
        User notfoundTeacher = new User(3L, "Новый учитель", "пароль", "электронная почта",
                "телефон", Role.ROLE_TEACHER);
        lessonWithNotFoundTeacher.setId(1L);
        lessonWithNotFoundTeacher.setLessonTime(lesson1.getLessonTime());
        lessonWithNotFoundTeacher.setTeacher(notfoundTeacher);
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson1));
        when(userRepository.findUserByName(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.updateLesson(lessonWithNotFoundTeacher));
        verify(userRepository, times(1)).findUserByName(anyString());
        verify(studentClassRepository, times(0)).findByTitle(anyString());
        verify(predmetRepository, times(0)).findByTitle(anyString());
        verify(homeworkRepository, times(0)).findByDescription(anyString());
        verify(lessonRepository, times(0)).save(any());
    }

    @DisplayName("Проверка получения всех уроков")
    @Test
    void getAllLessons() {
        when(lessonRepository.findAll()).thenReturn(List.of(lesson1, lesson2));
        List<Lesson> allLessons = lessonService.getAllLessons();
        assertThat(allLessons, contains(lesson1, lesson2));
        verify(lessonRepository).findAll();
    }

    @DisplayName("Проверка поиска уроков по учителю")
    @Test
    void searchLessonsForTeacher() {
        when(lessonRepository.searchLessonsForTeacher(anyString(), anyString(), anyString(), any(), any())).thenReturn(
                Optional.of(List.of(lesson2)));
        List<Lesson> lessons = lessonService.searchLessonsForTeacher("тест", "тест",
                "тест", LocalDateTime.now(), LocalDateTime.now());
        assertThat(lessons, contains(lesson2));
        verify(lessonRepository).searchLessonsForTeacher(anyString(), anyString(), anyString(), any(), any());
    }

    @DisplayName("Проверка перегруженного метода поиска уроков по учителю")
    @Test
    void testSearchLessonsForTeacher() {
        when(lessonRepository.searchAllLessonsForTeacher(anyString(), any(), any())).thenReturn(
                Optional.of(List.of(lesson1)));
        List<Lesson> lessons = lessonService.searchLessonsForTeacher("тест", LocalDateTime.now(),
                LocalDateTime.now());
        assertThat(lessons, contains(lesson1));
        verify(lessonRepository).searchAllLessonsForTeacher(anyString(), any(), any());
    }
}