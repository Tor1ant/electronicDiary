package com.sberbank.may.mark.controller;

import com.sberbank.may.lesson.service.LessonService;
import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.mark.service.MarkService;
import com.sberbank.may.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mark")
public class MarkController {

    private final StudentService studentService;
    private final MarkService markService;
    private final LessonService lessonService;

    @PostMapping("/add")
    public String showForm(@RequestParam("id") long id, @ModelAttribute("mark") Mark mark, Model model) {
        model.addAttribute("students", studentService.searchAllStudentsOnLesson(id));
        model.addAttribute("lesson", lessonService.findLessonById(id));
        return "mark_pages/create_mark";
    }

    @PostMapping("/saveMark")
    public String saveStudent(@ModelAttribute("mark") Mark mark) {
        markService.saveMark(mark);
        return "redirect:/lesson/searchMyLesson";
    }

    @PostMapping("/get")
    public String showStudentMarks(@RequestParam("id") long id, Model model) {
        model.addAttribute("students", markService.searchStudentsMarksOnLesson(id));
        model.addAttribute("lesson", lessonService.findLessonById(id));
        return "mark_pages/markList";
    }

    @PostMapping("/delete")
    public String deleteMark(@RequestParam("id") long id) {
        markService.deleteById(id);
        return "redirect:/lesson/searchMyLesson";
    }

    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Mark mark = markService.findMarkById(id);
        model.addAttribute("mark", mark);
        return "mark_pages/update_mark";
    }

    @PostMapping("/editMark")
    public String updateMark(@ModelAttribute("mark") Mark mark) {
        markService.patchMark(mark);
        return "redirect:/lesson/searchMyLesson";
    }
}
