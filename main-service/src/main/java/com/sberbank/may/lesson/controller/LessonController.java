package com.sberbank.may.lesson.controller;

import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.service.LessonService;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.service.PredmetService;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import com.sberbank.may.user.dto.UserDto;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final StudentClassService studentClassService;
    private final PredmetService predmetService;

    @GetMapping("/lessonForm")
    public String showCreationForm(Model model) {
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("teachers", userService.getAllTeachers());
        model.addAttribute("classes", studentClassService.searchAllClass());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "lesson_pages/create_lesson";
    }

    @PostMapping("/saveLesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.createLesson(lesson);
        return "redirect:/lesson/lessonForm";
    }

    @GetMapping("/searchLessonForm")
    public String showSearchUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("teachers", userService.getAllTeachers());
        model.addAttribute("classes", studentClassService.searchAllClass());
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "lesson_pages/lesson_search";
    }

    @GetMapping("/search")
    public String searchLessons(@RequestParam("teacherName") String teacherName,
            @RequestParam("className") String className,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("lessonTime") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime lessonTime,
            Model model) {
        List<Lesson> lessons = lessonService.searchLessons(teacherName, className, subjectName, lessonTime);
        model.addAttribute("lessons", lessons);
        return "lesson_pages/lesson_list";
    }

    @PostMapping("/delete")
    public String deleteLesson(@RequestParam("id") long id) {
        lessonService.deleteById(id);
        return "redirect:/lesson/searchLessonForm";
    }

    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Lesson lesson = lessonService.findLessonById(id);
        Set<User> teachers = userService.getAllTeachers();
        List<Predmet> predmets = predmetService.getAllPredmets();
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("lesson", lesson);
        model.addAttribute("teachers", teachers);
        model.addAttribute("predmets", predmets);
        model.addAttribute("studentClasses", studentClasses);
        return "lesson_pages/update_lesson";
    }

    @PostMapping("/update")
    public String updateLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.updateLesson(lesson);
        return "redirect:/lesson/searchLessonForm";
    }

    @GetMapping("/allLessons")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonService.getAllLessons());
        return "lesson_pages/lesson_list";
    }
}
