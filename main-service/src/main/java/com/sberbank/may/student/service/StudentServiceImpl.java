package com.sberbank.may.student.service;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.student.dto.ReportData;
import com.sberbank.may.student.dto.ReportItem;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final MarkRepository markRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

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
                .stream()
                .peek(mark -> lessonWithMarkOuts.get(mark.getLesson().getId()).setMark(mark.getValue()))
                .close();
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


    public Mono<byte[]> getAvgMarkReport(Long studentId) {
/*        List<Long> lessonIds = lessonRepository.findLessonIdsForYear()
                .stream()
                .filter(id -> {
                    Lesson lesson = lessonRepository.findById(id).orElse(null);
                    return lesson != null && lesson.getLessonTime().getYear() == LocalDate.now().getYear();
                })
                .collect(Collectors.toList());
        List<Mark> studentMarks = markRepository.findStudentMarksForLesson(lessonIds, studentId);
        markRepository.fin

        double total = 0;
        int count = 0;
        for (Mark mark : studentMarks) {
            total += mark.getValue();
            count++;
        }

        double average = total / count;

        ReportData reportData = new ReportData();
        reportData.setReportItems(new ArrayList<>());

        ReportItem reportItem = new ReportItem();
        reportItem.setFirstName("Фамилия ученика"); // Замените на фактическую фамилию ученика
        reportItem.setAverageGrade(Double.toString(average));
        reportItem.setPredmet("Название предмета");

        reportData.getReportItems().add(reportItem);

        return webClientBuilder.build()
                .post()
                .uri("/report-avg")
                .bodyValue(reportData)
                .accept(MediaType.APPLICATION_PDF)
                .retrieve()
                .bodyToMono(byte[].class);*/
        return null;
    }
}

