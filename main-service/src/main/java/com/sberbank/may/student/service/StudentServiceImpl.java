package com.sberbank.may.student.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.lesson.repository.LessonRepository;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.repository.MarkRepository;
import com.sberbank.may.student.dto.ReportData;
import com.sberbank.may.student.dto.ReportItem;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.repository.StudentRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public List<Student> searchAllStudentsOnLesson(long id) {
        return studentRepository.searchAllStudentsOnLesson(id)
                .orElseThrow(() -> new NotFoundException("Ученики с id урока = " + id + " не найдены"));
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
                .forEach(mark -> lessonWithMarkOuts.get(mark.getLesson().getId()).setMark(mark.getValue()));

        return new ArrayList<>(lessonWithMarkOuts.values());
    }

    @Override
    public List<LessonWithMarkOut> getStudentMarks(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom,
            LocalDateTime lessonTimeTo) {
        return markRepository.findStudentMarksForLessonByPredmet(studentId,
                        predmetId, lessonTimeFrom, lessonTimeTo).stream()
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

    @SneakyThrows
    @Override
    public Mono<byte[]> getAvgMarkReport(Long studentId, Long predmetId, LocalDateTime lessonTimeFrom,
            LocalDateTime lessonTimeTo) {

        List<Mark> studentMarks = markRepository.findStudentMarkByPredmetAndDates(studentId, lessonTimeFrom,
                lessonTimeTo, predmetId);
        OptionalDouble average = studentMarks.stream().mapToInt(Mark::getValue).average();

        ReportData reportData = new ReportData();
        reportData.setReportItems(new ArrayList<>());

        ReportItem reportItem = new ReportItem();
      //  reportItem.setFirstName(String.valueOf(studentId));
       // reportItem.setAverageGrade(Double.toString(average.orElse(0.0)));
      //  reportItem.setPredmet(String.valueOf(predmetId));
        reportItem.setFirstName("Имя");
        reportItem.setAverageGrade(Double.toString(average.orElse(0.0)));
        reportItem.setPredmet("Алгебра");

        reportData.getReportItems().add(reportItem);

/*        webClientBuilder.build()
                .get()
                .uri("http://localhost:7070/report-avg")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(byte[].class);*/
        WebClient webClient = WebClient.create("http://localhost:7070");
        return webClient.post()
                .uri("/report-avg")
                .header(StandardCharsets.UTF_8.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reportData)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(byte[].class);
    }
}

