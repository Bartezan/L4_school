-- liquibase formatted sql

--changeset BIE:1
CREATE INDEX student_name_index ON student (name) ;
--changeset BIE:2
CREATE INDEX faculty_name_color ON faculty (name,color);