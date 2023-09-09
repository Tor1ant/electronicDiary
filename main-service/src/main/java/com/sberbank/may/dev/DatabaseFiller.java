package com.sberbank.may.dev;

import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.HomeworkRepository;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import com.sberbank.may.user.enums.Role;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = "dev")
@Configuration
@RequiredArgsConstructor
public class DatabaseFiller {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final StudentClassRepository studentClassRepository;
    private final PredmetRepository predmetRepository;
    private final StudentRepository studentRepository;
    private final HomeworkRepository homeworkRepository;
    private final MarkRepository markRepository;

    @PostConstruct
    public void fillDateBase() {
        if (userRepository.count() > 0) {
            return;
        }
        User admin = new User(null, "admin", "admin", "admintest@mail.ru", "+79119756817",
                Role.ROLE_ADMIN);
        User teacher = new User(null, "Учитель", "Пароль", "test@mail.ru", "+79995363450",
                Role.ROLE_TEACHER);
        User teacher1 = new User(null, "Учитель1", "Пароль", "test1@mail.ru", "+79995363451",
                Role.ROLE_TEACHER);
        User teacher2 = new User(null, "Учитель2", "Пароль", "test2@mail.ru", "+79995363452",
                Role.ROLE_TEACHER);
        User parent = new User(null, "Родитель", "Пароль", "testParent@mail.ru", "+79992453580",
                Role.ROLE_PARENT);
        User parent1 = new User(null, "Родитель1", "Пароль", "testParent1@mail.ru", "+79992453581",
                Role.ROLE_PARENT);
        User parent2 = new User(null, "Родитель2", "Пароль", "testParent2@mail.ru", "+79992453582",
                Role.ROLE_PARENT);
        admin = userRepository.saveAndFlush(admin);
        teacher = userRepository.saveAndFlush(teacher);
        teacher1 = userRepository.saveAndFlush(teacher1);
        teacher2 = userRepository.saveAndFlush(teacher2);
        parent = userRepository.saveAndFlush(parent);
        parent1 = userRepository.saveAndFlush(parent1);
        parent2 = userRepository.saveAndFlush(parent2);
        Predmet predmet = new Predmet();
        predmet.setTitle("Алгебра");
        predmet = predmetRepository.saveAndFlush(predmet);
        Predmet predmet1 = new Predmet();
        predmet1.setTitle("Биология");
        predmet1 = predmetRepository.saveAndFlush(predmet1);
        Predmet predmet2 = new Predmet();
        predmet2.setTitle("Русский язык");
        predmet2 = predmetRepository.saveAndFlush(predmet2);
        StudentClass studentClass = new StudentClass();
        studentClass.setTitle("5А");
        studentClassRepository.saveAndFlush(studentClass);
        StudentClass studentClass1 = new StudentClass();
        studentClass1.setTitle("6Б");
        studentClassRepository.saveAndFlush(studentClass1);
        StudentClass studentClass2 = new StudentClass();
        studentClass2.setTitle("7А");
        studentClassRepository.saveAndFlush(studentClass2);
        Student student = new Student();
        student.setUser(Set.of(parent));
        student.setStudentClass(studentClass);
        student.setName("Студент");
        student = studentRepository.saveAndFlush(student);
        Student student1 = new Student();
        student1.setUser(Set.of(parent2));
        student1.setStudentClass(studentClass2);
        student1.setName("Студент1");
        student1 = studentRepository.saveAndFlush(student1);
        Student student2 = new Student();
        student2.setUser(Set.of(parent1, parent2));
        student2.setStudentClass(studentClass1);
        student2.setName("Студент2");
        student2 = studentRepository.saveAndFlush(student2);
        Homework homework = new Homework();
        homework.setDescription("Ничего не задано");
        homework = homeworkRepository.saveAndFlush(homework);
        Lesson lesson = new Lesson();
        lesson.setPredmet(predmet);
        lesson.setTeacher(teacher);
        lesson.setStudentClass(studentClass);
        lesson.setLessonTime(LocalDateTime.now().plusDays(1));
        lesson.setHomework(homework);
        lessonRepository.save(lesson);
        Lesson lesson1 = new Lesson();
        lesson1.setPredmet(predmet1);
        lesson1.setTeacher(teacher1);
        lesson1.setStudentClass(studentClass1);
        lesson1.setLessonTime(LocalDateTime.now());
        lesson1.setHomework(homework);
        lessonRepository.save(lesson1);
        Mark mark = new Mark();
        mark.setLesson(lesson);
        mark.setStudent(student);
        mark.setValue(5);
        markRepository.saveAndFlush(mark);
    }
}
