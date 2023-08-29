package com.sberbank.may.lesson.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.config.name=application-test",
        "spring.config.location=classpath:application-test.yaml"
}, webEnvironment = WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class LessonServiceImplIntegrationTest {

    private final LessonService lessonService;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final StudentClassRepository studentClassRepository;
    private final PredmetRepository predmetRepository;
    private final StudentRepository studentRepository;

    private User teacher1;
    private Predmet subject1;
    private StudentClass studentClass1;
    private Lesson lesson1;
    private Student student1;

    private User parent2;
    private User parent3;

    @BeforeEach
    void setUp() {
        teacher1 = new User(null, "Румянцева Анастасия Ивановна", "password",
                "testMail@mail.ru", "+79211554785",
                Role.ROLE_TEACHER);
        User teacher2 = new User(null, "Павлов Михаил Фёдорович", "password222",
                "Mihail@google.com", "+79211554785",
                Role.ROLE_TEACHER);
        User parent1 = new User(null, " Чехов Антон Павлович", "мертвыедуши",
                "goldenAge@mail.ru", "+79214568752",
                Role.ROLE_PARENT);
        parent2 = new User(null, "Расторгуева Мария Александровна", "zdanie255",
                "MAR@mail.ru", "+7523654789",
                Role.ROLE_PARENT);
        parent3 = new User(null, "Расторгуев Павел Владимирович", "1998",
                "PVR@mail.ru", "+79635284512",
                Role.ROLE_PARENT);
        subject1 = new Predmet();
        subject1.setTitle("Математика");
        Predmet subject2 = new Predmet();
        subject2.setTitle("Русский язык");
        studentClass1 = new StudentClass();
        studentClass1.setTitle("5А");
        StudentClass studentClass2 = new StudentClass();
        studentClass2.setTitle("11Б");
        student1 = new Student();
        student1.setName("Расторгуев Максим Павлович");
        student1.setUser(Set.of(parent2, parent3));
        student1.setStudentClass(studentClass1);
        Student student2 = new Student();
        student2.setName("Чехов Михаил Антонович");
        student2.setUser(Set.of(parent1));
        student2.setStudentClass(studentClass2);
        Homework homework1 = new Homework();
        homework1.setDescription("Сделать что-нибудь");
        lesson1 = new Lesson();
        lesson1.setLessonTime(LocalDateTime.now());
        lesson1.setHomework(homework1);
        lesson1.setTeacher(teacher1);
        lesson1.setPredmet(subject1);
        lesson1.setStudentClass(studentClass1);
        Lesson lesson2 = new Lesson();
        lesson2.setStudentClass(studentClass2);
        lesson2.setLessonTime(LocalDateTime.now().plusDays(1));
        lesson2.setTeacher(teacher2);
        lesson2.setHomework(homework1);
        lesson2.setPredmet(subject2);
        Mark mark1 = new Mark();
        mark1.setLesson(lesson1);
        mark1.setStudent(student1);
        mark1.setValue(5);
    }

    @DisplayName("Проверка создания урока")
    @Test()
    void createLesson() {
        userRepository.save(teacher1);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        lessonRepository.findById(1L).ifPresent(lessonFromDbB -> assertThat(lesson1, is(lessonFromDbB)));
    }

    @DisplayName("Проверка поиска уроков")
    @Test
    void searchLessons() {
        userRepository.save(teacher1);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        List<Lesson> lessons = lessonService.searchLessons("Румянцева Анастасия Ивановна", "5А",
                "Математика",
                lesson1.getLessonTime());
        assertThat(lessons, contains(lesson1));
    }

    @DisplayName("Проверка удаления урока")
    @Test
    void deleteById() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        assertThat(lessonRepository.findAll(), contains(lesson1));
        lessonService.deleteById(1L);
        List<Lesson> all = lessonRepository.findAll();
        assertThat(all, empty());
    }

    @DisplayName("Проверка поиска урока по id")
    @Test
    void findLessonById() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        assertThat(lessonService.findLessonById(1L), is(lesson1));
    }

    @DisplayName("Проверка обновления урока")
    @Test
    void updateLesson() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        Lesson foundLesson = lessonRepository.findById(1L).orElseThrow();
        Homework newHomework = new Homework();
        newHomework.setDescription("Новая домашняя работа");
        foundLesson.setHomework(newHomework);
        lessonService.updateLesson(foundLesson);
        assertThat(lessonRepository.findById(1L).orElseThrow().getHomework().getDescription(),
                equalTo(newHomework.getDescription()));
    }

    @DisplayName("Проверка получения всех уроков")
    @Test
    void getAllLessons() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);
        assertThat(lessonService.getAllLessons(), contains(lesson1));
    }

    @DisplayName("Проверка поиска уроков учителя")
    @Test
    void searchLessonsForTeacher() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);

        List<Lesson> lessons = lessonService.searchLessonsForTeacher(teacher1.getName(), studentClass1.getTitle(),
                subject1.getTitle(),
                lesson1.getLessonTime(), lesson1.getLessonTime().plusDays(3));
        assertThat(lessons, contains(lesson1));
    }

    @DisplayName("Проверка перегруженного метода поисков уроков учителя")
    @Test
    void testSearchLessonsForTeacher() {
        userRepository.save(teacher1);
        userRepository.save(parent2);
        userRepository.save(parent3);
        predmetRepository.save(subject1);
        studentClassRepository.save(studentClass1);
        studentRepository.save(student1);
        lessonService.createLesson(lesson1);

        List<Lesson> lessons = lessonService.searchLessonsForTeacher(teacher1.getName(), lesson1.getLessonTime(),
                lesson1.getLessonTime().plusDays(3));
        assertThat(lessons, contains(lesson1));
    }
}