package com.sberbank.may.predmet.controller;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.service.PredmetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequiredArgsConstructor
@RequestMapping("/predmet")
public class PredmetController {

    private final PredmetService predmetService;

    @GetMapping("/predmetForm")
    public String showCreationForm(@ModelAttribute("predmet") Predmet predmet) {
        return "create_predmet";
    }

    @PostMapping("/savePredmet")
    public String savePredmet(@ModelAttribute("predmet") Predmet predmet) {
        predmetService.createPredmet(predmet);
        return "redirect:/predmet/predmetForm";
    }
}
