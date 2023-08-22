package com.sberbank.may.lesson.repository;

import com.sberbank.may.lesson.model.Homework;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

   Optional<Homework> findByDescription(String description);
}
