package com.sberbank.may.lesson.controller;

import com.sberbank.may.user.dto.UserDto;
import org.springframework.ui.Model;
import com.sberbank.may.lesson.model.Lesson;
import com.sberbank.may.lesson.service.LessonService;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/lessonForm")
    public String showCreationForm(@ModelAttribute("lesson") Lesson lesson) {
        lesson.setTeacher(new User());
        lesson.setStudentClass(new StudentClass());
        lesson.setPredmet(new Predmet());
        return "lesson_pages/create_lesson";
    }

    @PostMapping("/saveLesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonService.createLesson(lesson);
        return "redirect:/lesson/lessonForm";
    }

    @GetMapping("/searchLessonForm")
    public String showSearchUserForm(@ModelAttribute("user") UserDto userDto) {
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


}
