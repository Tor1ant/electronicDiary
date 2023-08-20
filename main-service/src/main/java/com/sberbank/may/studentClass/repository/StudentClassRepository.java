package com.sberbank.may.studentClass.repository;

import com.sberbank.may.studentClass.model.StudentClass;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

   Optional<StudentClass> findByTitle(String title);
    List<StudentClass> findStudentClassByTitleLikeIgnoreCase(String title);
}
