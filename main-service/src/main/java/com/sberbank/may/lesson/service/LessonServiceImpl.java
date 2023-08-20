package com.sberbank.may.lesson.service;

import com.sberbank.may.exception.NotFoundException;
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
        User teacher = userRepository.findUserByName(lesson.getTeacher().getName()).orElseThrow(
                () -> new NotFoundException("Пользователь с именем " + lesson.getTeacher().getName() + " не найден"));
        StudentClass studentClass = studentClassRepository.findByTitle(lesson.getStudentClass().getTitle()).orElseThrow(
                () -> new NotFoundException("Класс с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        Predmet predmet = predmetRepository.findByTitle(lesson.getPredmet().getTitle()).orElseThrow(
                () -> new NotFoundException(
                        "Предмет с названием " + lesson.getStudentClass().getTitle() + " не найден"));
        lesson.setTeacher(teacher);
        lesson.setStudentClass(studentClass);
        lesson.setPredmet(predmet);
        homeworkRepository.save(lesson.getHomework());
        lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> searchLessons(String teacherName, String className, String subjectName,
            LocalDateTime lessonTime) {
        return lessonRepository.searchLesson(teacherName, className, subjectName, lessonTime)
                .orElseThrow(() -> new NotFoundException("По заданным параметрам уроки не найдены"));
    }

    @Override
    public void deleteById(long id) {
        lessonRepository.deleteById(id);
    }
}
