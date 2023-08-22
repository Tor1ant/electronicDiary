package com.sberbank.may.predmet.service;

import com.sberbank.may.predmet.model.Predmet;
import java.util.List;

public interface PredmetService {

    void createPredmet(Predmet predmet);

    List<Predmet> getAllPredmets();

    void deleteById(long id);
}
