package com.sberbank.may.lesson.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.HomeworkRepository;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final StudentClassRepository studentClassRepository;
    private final PredmetRepository predmetRepository;
    private final HomeworkRepository homeworkRepository;

    @Transactional
    @Override
    public void createLesson(Lesson lesson) {
        User teacher = userRepository.findById(lesson.getTeacher().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь с именем " + lesson.getTeacher().getName() + " не найден"));
        StudentClass studentClass = studentClassRepository.findById(lesson.getStudentClass().getId()).orElseThrow(
                () -> new NotFoundException("Класс с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        Predmet predmet = predmetRepository.findById(lesson.getPredmet().getId()).orElseThrow(
                () -> new NotFoundException(
                        "Предмет с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        lesson.setTeacher(teacher);
        lesson.setStudentClass(studentClass);
        lesson.setPredmet(predmet);
        if (lesson.getHomework().getDescription().isBlank()) {
            lesson.getHomework().setDescription("Ничего не задано");
        }
        homeworkRepository.save(lesson.getHomework());
        lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> searchLessons(String teacherName, String className, String subjectName,
            LocalDateTime lessonTime) {
        return lessonRepository.searchLesson(teacherName, className, subjectName, lessonTime)
                .orElseThrow(() -> new NotFoundException("По заданным параметрам уроки не найдены"));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public Lesson findLessonById(long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("урок для изменения не найден"));
    }

    @Transactional
    @Override
    public void updateLesson(Lesson lesson) {
        Lesson lessonForUpdate = findLessonById(lesson.getId());
        User teacher;
        StudentClass studentClass;
        Predmet predmet;
        Homework homework;
        if (!lesson.getTeacher().getName().equals(lessonForUpdate.getTeacher().getName())) {
            teacher = userRepository.findUserByName(lesson.getTeacher().getName()).orElseThrow(
                    () -> new NotFoundException(
                            "Пользователь с именем " + lesson.getTeacher().getName() + " не найден"));
        } else {
            teacher = lessonForUpdate.getTeacher();
        }
        if (!lesson.getStudentClass().getTitle().equals(lessonForUpdate.getStudentClass().getTitle())) {
            studentClass = studentClassRepository.findByTitle(lesson.getStudentClass().getTitle()).orElseThrow(
                    () -> new NotFoundException(
                            "Класс с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        } else {
            studentClass = lessonForUpdate.getStudentClass();
        }
        if (!lesson.getPredmet().getTitle().equals(lessonForUpdate.getPredmet().getTitle())) {
            predmet = predmetRepository.findByTitle(lesson.getPredmet().getTitle()).orElseThrow(
                    () -> new NotFoundException(
                            "Предмет с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        } else {
            predmet = lessonForUpdate.getPredmet();
        }
        if (!lesson.getHomework().getDescription().equals(lessonForUpdate.getHomework().getDescription())) {
            homework = homeworkRepository.findByDescription(lesson.getHomework().getDescription())
                    .orElse(homeworkRepository.save(lesson.getHomework()));
        } else {
            homework = lessonForUpdate.getHomework();
        }
        if (Objects.nonNull(lesson.getLessonTime())) {
            lessonForUpdate.setLessonTime(lesson.getLessonTime());
        }
        lessonForUpdate.setTeacher(teacher);
        lessonForUpdate.setStudentClass(studentClass);
        lessonForUpdate.setPredmet(predmet);
        lessonForUpdate.setHomework(homework);
        lessonRepository.save(lessonForUpdate);
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> searchLessonsForTeacher(String teacherName, String className, String subjectName,
                                                LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo) {
        return lessonRepository.searchLessonsForTeacher(teacherName, className, subjectName, lessonTimeFrom, lessonTimeTo)
                .orElseThrow(() -> new NotFoundException("По заданным параметрам уроки не найдены"));
    }

    @Override
    public List<Lesson> searchLessonsForTeacher(String teacherName, LocalDateTime lessonTimeFrom, LocalDateTime lessonTimeTo) {
        return lessonRepository.searchAllLessonsForTeacher(teacherName, lessonTimeFrom, lessonTimeTo)
                .orElseThrow(() -> new NotFoundException("По заданным параметрам уроки не найдены"));
    }
}
