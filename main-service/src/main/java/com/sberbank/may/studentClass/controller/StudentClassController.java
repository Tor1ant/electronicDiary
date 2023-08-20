package com.sberbank.may.studentClass.controller;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/class")
public class StudentClassController {

    private final StudentClassService studentClassService;

    @PostMapping("/saveClass")
    public String saveClass(@ModelAttribute("studentClass") StudentClass studentClass) {
        studentClassService.saveClass(studentClass);
        return "redirect:/class/classForm";
    }

    @GetMapping("/classForm")
    public String showClassForm(@ModelAttribute("studentClass") StudentClass studentClass) {
        return "class_pages/create_class";
    }

    @GetMapping("/searchClassForm")
    public String showSearchClassForm(@ModelAttribute("class") StudentClassDto studentClassDto) {
        return "class_pages/classSearch";
    }

    @GetMapping("/searchClass")
    public String searchClass(@ModelAttribute("class") StudentClassDto studentClassDto, Model model) {
        List<StudentClass> studentClasses = studentClassService.searchClass(studentClassDto);
        model.addAttribute("studentClasses", studentClasses);
        return "class_pages/classList";
    }

    @PostMapping("/delete")
    public String deleteClass(@RequestParam("id") long id) {
        studentClassService.deleteById(id);
        return "redirect:/class/allClasses";
    }

    @GetMapping("/allClasses")
    public String searchAllClass(Model model) {
        List<StudentClass> studentClasses = studentClassService.searchAllClass();
        model.addAttribute("studentClasses", studentClasses);
        return "class_pages/classList";
    }

    @PostMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        StudentClass studentClass = studentClassService.findStudentClassById(id);
        model.addAttribute("studentClass", studentClass);
        return "class_pages/update_class";
    }

    @PostMapping("/editClass")
    public String updateUser(@ModelAttribute("studentClass") StudentClass studentClass) {
        studentClassService.patchClass(studentClass);
        return "redirect:/class/allClasses";
    }
}
