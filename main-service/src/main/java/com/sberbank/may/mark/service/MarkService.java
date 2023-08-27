package com.sberbank.may.mark.service;

import com.sberbank.may.mark.model.Mark;
import com.sberbank.may.student.dto.StudentWithMarkOut;

import java.util.List;

public interface MarkService {
    void saveMark(Mark mark);

    List<StudentWithMarkOut> searchStudentsMarksOnLesson(long id);

    void deleteById(long id);

    Mark findMarkById(long id);

    void patchMark(Mark mark);
}
