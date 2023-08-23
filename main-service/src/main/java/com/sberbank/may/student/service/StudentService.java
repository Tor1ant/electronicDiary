package com.sberbank.may.student.service;

import com.sberbank.may.student.dto.StudentDto;
import com.sberbank.may.student.model.Student;

import java.util.List;

public interface StudentService {
    void saveStudent(Student student);

    List<Student> searchStudent(StudentDto studentDto);

    List<Student> searchAllStudents();

    Student findStudentById(long id);

    void deleteById(long id);

    void patchStudent(Student student);
}
