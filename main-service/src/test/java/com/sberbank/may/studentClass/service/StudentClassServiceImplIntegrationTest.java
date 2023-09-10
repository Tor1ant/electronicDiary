package com.sberbank.may.studentClass.service;

import com.sberbank.may.studentClass.dto.StudentClassDto;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.studentClass.repository.StudentClassRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Ignore
@SpringBootTest(properties = {
        "spring.config.name=application-test",
        "spring.config.location=classpath:application-test.yaml"
}, webEnvironment = WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class StudentClassServiceImplIntegrationTest {

//    private final StudentClassService studentClassService;
//    private final StudentClassRepository studentClassRepository;
//    private StudentClass studentClass1;
//    private StudentClass studentClass2;
//
//    @BeforeEach
//    @Transactional
//    void setUp() {
//        studentClass1 = new StudentClass();
//        studentClass1.setTitle("5А");
//        studentClass2 = new StudentClass();
//        studentClass2.setTitle("11Б");
//        studentClassService.saveClass(studentClass2);
//    }
//
//    @DisplayName("Проверка создания класса")
//    @Test()
//    void testSaveClass() {
//        studentClassService.saveClass(studentClass1);
//        studentClassRepository.findById(studentClass1.getId()).ifPresent(studentClassFromDbB -> assertThat(studentClass1, is(studentClassFromDbB)));
//    }
//
//    @DisplayName("Проверка поиска класса")
//    @Test
//    void testSearchClass() {
//        StudentClassDto studentClassDto = new StudentClassDto();
//        studentClassDto.setTitle(studentClass2.getTitle());
//        List<StudentClass> classes = studentClassService.searchClass(studentClassDto);
//        assertThat(classes, contains(studentClass2));
//    }
//
//    @DisplayName("Проверка удаления класса по id")
//    @Test
//    void testDeleteById() {
//        assertThat(studentClassRepository.findAll(), contains(studentClass2));
//        studentClassRepository.deleteById(studentClass2.getId());
//        List<StudentClass> all = studentClassRepository.findAll();
//        assertThat(all, empty());
//    }
//
//    @DisplayName("Проверка получения всех классов")
//    @Test
//    void testSearchAllClass() {
//        assertThat(studentClassService.searchAllClass(), contains(studentClass2));
//    }
//
//    @DisplayName("Проверка поиска класса по id")
//    @Test
//    void testFindStudentClassById() {
//        assertThat(studentClassService.findStudentClassById(studentClass2.getId()), is(studentClass2));
//    }
//
//    @DisplayName("Проверка обновления класса")
//    @Test
//    void testPatchClass() {
//        studentClassService.saveClass(studentClass1);
//        StudentClass foundClass = studentClassRepository.findById(studentClass1.getId()).orElseThrow();
//        foundClass.setTitle("4A");
//        studentClassService.patchClass(foundClass);
//        assertThat(studentClassRepository.findById(studentClass1.getId()).orElseThrow().getTitle(),
//                equalTo("4A"));
//    }
}