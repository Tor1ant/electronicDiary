package com.sberbank.may.studentClass.service;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;

import java.util.List;

public interface StudentClassService {
    void saveClass(StudentClass studentClass);

    List<StudentClass> searchClass(StudentClassDto studentClassDto);

    void deleteById(long id);

    List<StudentClass> searchAllClass();

    StudentClass findStudentClassById(long id);

    void patchClass(StudentClass studentClass);
}
