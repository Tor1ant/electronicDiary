package com.sberbank.may.dev;

import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.HomeworkRepository;
import com.sberbank.may.lesson.repository.LessonRepository;
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

    @PostConstruct
    public void fillDateBase() {
        User teacher = new User(null, "Учитель", "Пароль", "test@mail.ru", "+79995363450",
                Role.ROLE_TEACHER);
        User parent = new User(null, "Родитель", "Пароль", "testParent@mail.ru", "+79992453580",
                Role.ROLE_PARENT);
        teacher = userRepository.saveAndFlush(teacher);
        parent = userRepository.saveAndFlush(parent);
        Predmet predmet = new Predmet();
        predmet.setTitle("Алгебра");
        predmet = predmetRepository.saveAndFlush(predmet);
        StudentClass studentClass = new StudentClass();
        studentClass.setTitle("5А");
        studentClassRepository.saveAndFlush(studentClass);
        Student student = new Student();
        student.setUser(Set.of(parent));
        student.setStudentClass(studentClass);
        student.setName("Студент");
        student = studentRepository.saveAndFlush(student);
        Homework homework = new Homework();
        homework.setDescription("Ничего не задано");
        homework = homeworkRepository.saveAndFlush(homework);
        Lesson lesson = new Lesson();
        lesson.setPredmet(predmet);
        lesson.setTeacher(teacher);
        lesson.setStudentClass(studentClass);
        lesson.setLessonTime(LocalDateTime.now());
        lesson.setHomework(homework);
        lessonRepository.save(lesson);
    }
}
