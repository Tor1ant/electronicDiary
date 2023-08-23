drop table if exists users cascade;
drop table if exists students cascade;
drop table if exists predmets cascade;
drop table if exists student_classes cascade;
drop table if exists homeworks cascade;
drop table if exists lessons cascade;
drop table if exists student_lesson cascade;
drop table if exists student_user cascade;

create table users
(
    id       integer generated by default as identity,
    name     varchar not null,
    password varchar not null,
    email    varchar not null,
    phone    varchar not null,
    role     varchar not null,
    constraint users_pk
        primary key (id)
);

create table public.students
(
    id               integer generated by default as identity
        constraint students_pk
            primary key,
    name             varchar not null,
    student_class_id integer not null
);

create table public.predmets
(
    id    integer generated by default as identity
        constraint predmets_pk
            primary key,
    title varchar not null
        constraint predmets_pk2
            unique
);

create table public.student_classes
(
    id    integer generated by default as identity
        constraint student_classes_pk
            primary key,
    title varchar not null
);

create table public.homeworks
(
    id          integer generated by default as identity
        constraint homeworks_pk
            primary key,
    description varchar default 'Ничего не задано'::character varying not null
);

create table public.lessons
(
    id               integer generated by default as identity
        constraint lessons_pk
            primary key,
    lesson_time      timestamp not null,
    user_id          integer
        constraint lessons_users_id_fk
            references public.users
            on update cascade,
    student_class_id integer
        constraint lessons_student_classes_id_fk
            references public.student_classes
            on update cascade,
    predmet_id       integer
        constraint lessons_predmets_id_fk
            references public.predmets
            on update cascade,
    homework_id      integer
        constraint lessons_homeworks_id_fk
            references public.homeworks
            on update cascade
);

create table student_lesson
(
    id_mark    integer generated by default as identity
    constraint student_lesson_pk
    primary key,
    value      integer default 0,
    lesson_id  integer not null
    constraint student_lesson_lessons_id_fk
    references public.lessons,
    student_id integer not null
    constraint student_lesson_students_id_fk
    references public.students
);

create table student_user
(
    user_id    integer not null
        constraint student_user_users__id_fk
            references public.users,
    student_id integer not null
        constraint student_user_students_id_fk
            references public.students,
    constraint student_user_pk
        primary key (user_id, student_id)
);




