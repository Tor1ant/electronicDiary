package com.sberbank.may.predmet.controller;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.service.PredmetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/predmet")
public class PredmetController {

    private final PredmetService predmetService;

    @GetMapping("/predmetForm")
    public String showCreationForm(Model model) {
        model.addAttribute("predmet", new Predmet());
        return "predmet_pages/create_predmet";
    }

    @PostMapping("/savePredmet")
    public String savePredmet(@ModelAttribute("predmet") Predmet predmet) {
        predmetService.createPredmet(predmet);
        return "redirect:/predmet/allPredmets";
    }

    @GetMapping("/allPredmets")
    public String getAllLessons(Model model) {
        model.addAttribute("predmets", predmetService.getAllPredmets());
        return "predmet_pages/predmet_list";
    }

    @PostMapping("/delete")
    public String deleteLesson(@RequestParam("id") long id) {
        predmetService.deleteById(id);
        return "redirect:/predmet/allPredmets";
    }
}
