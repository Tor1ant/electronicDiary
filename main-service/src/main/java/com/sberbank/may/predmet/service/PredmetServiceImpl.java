package com.sberbank.may.predmet.service;

import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.predmet.repository.PredmetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PredmetServiceImpl implements PredmetService {

    private final PredmetRepository predmetRepository;

    @Transactional
    @Override
    public void createPredmet(Predmet predmet) {
        predmetRepository.findByTitle(predmet.getTitle()).ifPresent(existingPredmet -> predmetRepository.deleteById(
                existingPredmet.getId()));
        predmetRepository.save(predmet);
    }

    @Override
    public List<Predmet> getAllPredmets() {
        return predmetRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        predmetRepository.deleteById(id);
    }
}
