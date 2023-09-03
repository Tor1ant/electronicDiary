package com.sberbank.may.student.controller;

import com.sberbank.may.predmet.service.PredmetService;
import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;
import com.sberbank.may.student.service.StudentService;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер обращений к URL "/student".
 * Занимается обработкой HTTP-запросов, связанных со студентами.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentClassService studentClassService;
    private final UserService userService;
    private final PredmetService predmetService;

    /**
     * Сохраняет студента в базе данных и перенаправляет на форму студента.
     * @param student объект студента для сохранения.
     * @return Redirect URL.
     */
    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/student/studentForm";
    }

    /**
     * Отображает форму создания студента.
     * @param student объект студента для формы.
     * @param model модель данных для обработки.
     * @return View name.
     */
    @GetMapping("/studentForm")
    public String showStudentForm(@ModelAttribute("student") Student student,
            Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        Set<User> users = userService.getAllParents();
        model.addAttribute("users", users);
        return "student_pages/create_student";
    }

    /**
     * Отображает форму поиска студента.
     * @param studentDto объект DTO студента для поиска.
     * @param model модель данных для обработки.
     * @return View name.
     */
    @GetMapping("/searchStudentForm")
    public String showSearchStudentForm(@ModelAttribute("student") StudentDto studentDto, Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        return "student_pages/studentSearch";
    }

    /**
     * Выполняет поиск студентов и отображает их список.
     * @param studentDto объект DTO студента для поиска.
     * @param model модель данных для обработки.
     * @return View name.
     */
    @GetMapping("/searchStudent")
    public String searchClass(@ModelAttribute("student") StudentDto studentDto, Model model) {
        List<Student> students = studentService.searchStudent(studentDto);
        model.addAttribute("students", students);
        return "student_pages/studentList";
    }

    /**
     * Отображает всех студентов.
     * @param model модель данных для обработки.
     * @return View name.
     */
    @GetMapping("/allStudents")
    public String searchAllStudents(Model model) {
        List<Student> students = studentService.searchAllStudents();
        model.addAttribute("students", students);
        return "student_pages/studentList";
    }

    /**
     * Удаляет студента по его идентификатору и перенаправляет на страницу со списком всех студентов.
     * @param id идентификатор студента.
     * @return Redirect URL.
     */
    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("id") long id) {
        studentService.deleteById(id);
        return "redirect:/student/allStudents";
    }

    /**
     * Отображает форму редактирования студента.
     * @param id идентификатор студента.
     * @param model модель данных для обработки.
     * @return View name.
     */
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

    /**
     * Обновляет студента в базе данных и перенаправляет на страницу со списком всех студентов.
     * @param student объект студента для обновления.
     * @return Redirect URL.
     */
    @PostMapping("/editStudent")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.patchStudent(student);
        return "redirect:/student/allStudents";
    }

    /**
     * Отображает оценки студентов.
     * @param model модель данных для обработки.
     * @return View name.
     */
    @GetMapping("/studentMarks")
    public String getStudentMarks(Model model) {
        model.addAttribute("students", studentService.searchAllStudents());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "student_pages/student_marks";
    }

    /**
     * Генерирует отчет об усредненных оценках студентов за выбранный интервал времени.
     * @param studentId идентификатор студента.
     * @param predmetId идентификатор предмета.
     * @param lessonTimeFrom начальное время уроков.
     * @param lessonTimeTo конечное время уроков.
     * @return Файл с отчетом.
     */
    @PostMapping("/studentsReport")
    public ResponseEntity<byte[]> generateStudentsReport(@RequestParam("studentId") Long studentId,
            @RequestParam("predmetId") Long predmetId,
            @RequestParam("lessonTimeFrom") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeFrom,
            @RequestParam("lessonTimeTo") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeTo) {

        ResponseEntity<byte[]> responseEntity = studentService.getAvgMarkReport(studentId, predmetId, lessonTimeFrom,
                lessonTimeTo);

        HttpHeaders headers = responseEntity.getHeaders();
        return ResponseEntity.ok().headers(headers).body(responseEntity.getBody());
    }
}