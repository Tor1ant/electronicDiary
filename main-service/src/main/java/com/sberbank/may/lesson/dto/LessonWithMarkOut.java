package com.sberbank.may.lesson.dto;

import com.sberbank.may.lesson.model.Homework;
import com.sberbank.may.predmet.model.Predmet;
import com.sberbank.may.studentClass.model.StudentClass;
import com.sberbank.may.user.model.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * Класс, представляющий урок со связанными данными об оценках.
 */
@Data
@Builder
public class LessonWithMarkOut {

    private Long id;
    private LocalDateTime lessonTime;
    private User teacher;
    private StudentClass studentClass;
    private Predmet predmet;
    private Homework homework;
    private Integer mark;
}
