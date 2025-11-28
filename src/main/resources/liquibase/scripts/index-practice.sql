-- liquibase formatted sql

--changeset BIE:1
--alter table student
CREATE INDEX student_name_index ON student (name) ;
--changeset BIE:2
--ALTER TABLE faculty
CREATE INDEX faculty_name_color ON faculty (name,color);