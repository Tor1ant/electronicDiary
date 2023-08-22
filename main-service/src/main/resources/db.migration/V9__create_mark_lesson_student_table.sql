create table mark_lesson_student
(
    value      integer not null,
    lesson_id  integer not null,
    student_id integer not null,
    constraint mark_lesson_lesson_pk
        primary key (lesson_id, student_id),
    constraint mark_lesson_student_lessons_id_fk
        foreign key (lesson_id) references lessons,
    constraint mark_lesson_student_students_id_fk
        foreign key (student_id) references students
);