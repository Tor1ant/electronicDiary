DROP TABLE IF EXISTS student_user cascade;


create table public.student_user
(
    user_id    bigint not null
        constraint student_user_users__id_fk
            references public.users,
    student_id bigint not null
        constraint student_user_students_id_fk
            references public.students,
    constraint student_user_pk
        primary key (user_id, student_id)
);