package com.sberbank.may.predmet.service;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredmetServiceImpl implements PredmetService {

    private final PredmetRepository predmetRepository;

    @Override
    public void createPredmet(Predmet predmet) {
        predmetRepository.save(predmet);
    }
}
