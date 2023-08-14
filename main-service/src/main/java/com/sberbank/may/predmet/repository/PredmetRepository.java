package com.sberbank.may.predmet.repository;

import com.sberbank.may.predmet.model.Predmet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {
}
