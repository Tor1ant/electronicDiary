package com.sberbank.may.student.controller;

import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.service.StudentService;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.lang.Long;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentClassService studentClassService;
    private final UserService userService;

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
    public Mono<ResponseEntity<byte[]>> generateStudentsReport(@RequestParam("studentId") Long studentId) {
        Mono<byte[]> reportBytesMono = studentService.getAvgMarkReport(studentId);
        return reportBytesMono.map(reportBytes -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            return ResponseEntity.ok().headers(headers).body(reportBytes);
        });
    }
}