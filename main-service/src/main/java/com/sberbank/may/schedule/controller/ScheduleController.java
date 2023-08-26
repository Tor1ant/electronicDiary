package com.sberbank.may.schedule.controller;

import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.student.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final StudentService studentService;
    private final PredmetRepository predmetRepository;

    @GetMapping("/schedule")
    public String showSchedulePage(Model model) {
        model.addAttribute("students", studentService.searchAllStudents());
        return "schedule_pages/schedule";
    }

    @PostMapping("/viewSchedule")
    public String viewSchedule(@RequestParam("studentId") Long studentId,
            @RequestParam("scheduleDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<LessonWithMarkOut> lessons = studentService.getStudentSchedule(studentId, date);
        model.addAttribute("lessons", lessons);
        return "schedule_pages/viewSchedule";
    }

    @GetMapping("/studentMarks")
    public String getStudentMarks(Model model) {
        model.addAttribute("students", studentService.searchAllStudents());
        model.addAttribute("predmets", predmetRepository.findAll());
        return "student_pages/student_marks";
    }

    @PostMapping("/viewMarks")
    public String viewMarks(@RequestParam("studentId") Long studentId, @RequestParam("predmetId") Long predmetId,
            Model model) {
        List<LessonWithMarkOut> lessons = studentService.getStudentMarks(studentId, predmetId);
        model.addAttribute("lessons", lessons);
        return "student_pages/student_marks_list";
    }
}
