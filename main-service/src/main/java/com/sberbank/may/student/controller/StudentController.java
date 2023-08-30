package com.sberbank.may.student.controller;

import com.sberbank.may.student.dto.ReportData;
import com.sberbank.may.student.dto.ReportItem;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.service.StudentService;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.lang.Long;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentClassService studentClassService;
    private final UserService userService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {

        studentService.saveStudent(student);
        return "redirect:/student/studentForm";
    }

    @GetMapping("/studentForm")
    public String showStudentForm(@ModelAttribute("student") Student student,
                                  Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        Set<User> users = userService.getAllParents();
        model.addAttribute("users", users);
        return "student_pages/create_student";
    }

    @GetMapping("/searchStudentForm")
    public String showSearchStudentForm(@ModelAttribute("student") StudentDto studentDto, Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        return "student_pages/studentSearch";
    }

    @GetMapping("/searchStudent")
    public String searchClass(@ModelAttribute("student") StudentDto studentDto, Model model) {
        List<Student> students = studentService.searchStudent(studentDto);
        model.addAttribute("students", students);
        return "student_pages/studentList";
    }

    @GetMapping("/allStudents")
    public String searchAllStudents(Model model) {
        List<Student> students = studentService.searchAllStudents();
        model.addAttribute("students", students);
        return "student_pages/studentList";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("id") long id) {
        studentService.deleteById(id);
        return "redirect:/student/allStudents";
    }

    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Student student = studentService.findStudentById(id);
        model.addAttribute("student", student);
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        Set<User> users = userService.getAllParents();
        model.addAttribute("users", users);
        return "student_pages/update_student";
    }

    @PostMapping("/editStudent")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.patchStudent(student);
        return "redirect:/student/allStudents";
    }


    @GetMapping("/studentsReport")
    public Mono<String> generateStudentsReport(@RequestParam("studentId") Long studentId,
            @RequestParam("predmetId") Long predmetId,
            @RequestParam("lessonTimeFrom") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeFrom,
            @RequestParam("lessonTimeTo") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeTo,
            Model model) {

        return studentService.getAvgMarkReport(studentId, predmetId, lessonTimeFrom, lessonTimeTo)
                .flatMap(average -> {
                    ReportData reportData = new ReportData();
                    reportData.setReportItems(new ArrayList<>());

                    ReportItem reportItem = new ReportItem();
                    reportItem.setFirstName(String.valueOf(studentId));
                    reportItem.setAverageGrade(String.valueOf(average));
                    reportItem.setPredmet(String.valueOf(lessonTimeFrom));

                    reportData.getReportItems().add(reportItem);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                   // HttpEntity<ReportData> requestEntity = new HttpEntity<>(reportData, headers);

                    return webClientBuilder.build()
                            .post()
                            .uri("/report-avg")
                            .bodyValue(reportData)
                            .accept(MediaType.APPLICATION_PDF)
                            .retrieve()
                            .bodyToMono(byte[].class)
                            .map(responseBody -> {
                                HttpHeaders responseHeaders = new HttpHeaders();
                                responseHeaders.setContentType(MediaType.APPLICATION_PDF);
                                responseHeaders.setContentDispositionFormData("attachment", "report.pdf");
                                model.addAttribute("reportData", reportData);
                                return "student_pages/student_marks";
                               // return ResponseEntity.ok().headers(responseHeaders).body(responseBody);
                            });
                });
    }
}