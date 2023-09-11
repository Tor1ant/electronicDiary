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
        User teacher3 = new User(null, "Учитель3", "Пароль", "test3@mail.ru", "+79995363453",
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
        Predmet predmet3 = new Predmet();
        predmet3.setTitle("Физкультура");
        predmet3 = predmetRepository.saveAndFlush(predmet3);
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
        Student student3 = new Student();
/*        student3.setUser(Set.of(parent1));
        student3.setStudentClass(studentClass);
        student3.setName("Студент3");
        studentRepository.saveAndFlush(student3);*/
/*        Student student4 = new Student();
        student4.setUser(Set.of(parent2));
        student4.setStudentClass(studentClass);
        student4.setName("Студент4");
        studentRepository.saveAndFlush(student4);*/
        Homework homework = new Homework();
        homework.setDescription("Ничего не задано");
        homework = homeworkRepository.saveAndFlush(homework);
        Lesson lesson = new Lesson();
        lesson.setPredmet(predmet);
        lesson.setTeacher(teacher);
        lesson.setStudentClass(studentClass);
        lesson.setLessonTime(LocalDateTime.of(2023,9, 11, 9, 0));
        lesson.setHomework(homework);
        lessonRepository.save(lesson);
        Lesson lesson1 = new Lesson();
        lesson1.setPredmet(predmet1);
        lesson1.setTeacher(teacher1);
        lesson1.setStudentClass(studentClass);
        lesson1.setLessonTime(LocalDateTime.of(2023,9, 11, 9, 55));
        lesson1.setHomework(homework);
        lessonRepository.save(lesson1);
        Lesson lesson2 = new Lesson();
        lesson2.setPredmet(predmet2);
        lesson2.setTeacher(teacher2);
        lesson2.setStudentClass(studentClass);
        lesson2.setLessonTime(LocalDateTime.of(2023,9, 11, 10, 50));
        lesson2.setHomework(homework);
        lessonRepository.save(lesson2);
        Lesson lesson3 = new Lesson();
        lesson3.setPredmet(predmet3);
        lesson3.setTeacher(teacher2);
        lesson3.setStudentClass(studentClass);
        lesson3.setLessonTime(LocalDateTime.of(2023,9, 11, 12, 10));
        lesson3.setHomework(homework);
        lessonRepository.save(lesson3);
        Lesson lesson4 = new Lesson();
        lesson4.setPredmet(predmet);
        lesson4.setTeacher(teacher);
        lesson4.setStudentClass(studentClass);
        lesson4.setLessonTime(LocalDateTime.of(2023,9, 11, 13, 0));
        lesson4.setHomework(homework);
        lessonRepository.save(lesson4);
        Mark mark = new Mark();
        mark.setLesson(lesson);
        mark.setStudent(student);
        mark.setValue(5);
        markRepository.saveAndFlush(mark);
        Mark mark1 = new Mark();
        mark1.setLesson(lesson1);
        mark1.setStudent(student);
        mark1.setValue(3);
        markRepository.saveAndFlush(mark1);
        Mark mark3 = new Mark();
        mark3.setLesson(lesson4);
        mark3.setStudent(student);
        mark3.setValue(4);
        markRepository.saveAndFlush(mark3);
    }
}
