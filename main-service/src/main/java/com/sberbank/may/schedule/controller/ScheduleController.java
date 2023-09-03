package com.sberbank.may.schedule.controller;

import com.sberbank.may.lesson.dto.LessonWithMarkOut;
import com.sberbank.may.predmet.repository.PredmetRepository;
import com.sberbank.may.student.service.StudentService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для работы с расписанием занятий и оценками студентов.
 */
@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final StudentService studentService;
    private final PredmetRepository predmetRepository;

    /**
     * Отображает страницу с расписанием уроков для всех студентов.
     * @param model модель в МВC структуре
     * @return шаблон страницы расписания
     */
    @GetMapping("/schedule")
    public String showSchedulePage(Model model) {
        model.addAttribute("students", studentService.searchAllStudents());
        return "schedule_pages/schedule";
    }

    /**
     * Представление расписания уроков конкретного студента в заданную дату.
     * @param studentId идентификатор студента
     * @param date дата
     * @param model модель в МВC структуре
     * @return шаблон страницы просмотра расписания
     */
    @PostMapping("/viewSchedule")
    public String viewSchedule(@RequestParam("studentId") Long studentId,
            @RequestParam("scheduleDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<LessonWithMarkOut> lessons = studentService.getStudentSchedule(studentId, date);
        model.addAttribute("lessons", lessons);
        return "schedule_pages/viewSchedule";
    }

    /**
     * Получает оценки студента.
     * @param model модель в МВC структуре
     * @return шаблон страницы оценок студента
     */
    @GetMapping("/studentMarks")
    public String getStudentMarks(Model model) {
        model.addAttribute("students", studentService.searchAllStudents());
        model.addAttribute("predmets", predmetRepository.findAll());
        return "student_pages/student_marks";
    }

    /**
     * Представление оценок конкретного студента по заданному предмету и времени.
     * @param studentId идентификатор студента
     * @param predmetId идентификатор предмета
     * @param lessonTimeFrom начало временного диапазона уроков
     * @param lessonTimeTo конец временного диапазона уроков
     * @param model модель в МВC структуре
     * @return шаблон страницы оценок студента
     */
    @PostMapping("/viewMarks")
    public String viewMarks(@RequestParam("studentId") Long studentId, @RequestParam("predmetId") Long predmetId,
            @RequestParam("lessonTimeFrom") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeFrom,
            @RequestParam("lessonTimeTo") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
            LocalDateTime lessonTimeTo,
            Model model) {
        List<LessonWithMarkOut> lessons = studentService.getStudentMarks(studentId, predmetId,lessonTimeFrom,lessonTimeTo);
        model.addAttribute("lessons", lessons);
        return "student_pages/student_marks_list";
    }
}
