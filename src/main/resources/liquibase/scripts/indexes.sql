-- liquibase formatted sql

-- changeset ars:create_indexes
 create index student_index on student (name);
 create index faculty_index on faculty (name, color);