-- С прошлых уроков у нас есть две таблицы: Student и Faculty.
create table faculty(
	id serial primary key,
	name varchar,
	color varchar
);
create table student(
	id serial primary key,
	name varchar,
	age int8,
	faculty_id serial references faculty (id)
);

insert into faculty values (1,'gryffindor','red');
insert into faculty values (2,'slytherin','green');
insert into faculty values (3,'hufflepuff','yellow');
insert into faculty values (4,'ravenclaw','blue');

select * from faculty;

insert into student values (1,25,'Anton', 2);
insert into student values (2,24,'Tatyana', 1);
insert into student values (3,30,'Natalya', 3);
insert into student values (4,45,'Olga', 4);

select * from student;

-- Необходимо для них создать следующие ограничения:
select s.name as name_student, s.age as age, f.name as name_faculty
from student s
join faculty f on s.faculty_id = f.id ;

-- Возраст студента не может быть меньше 16 лет.
alter table student
	add constraint check_age check (age >= 16);

-- Имена студентов должны быть уникальными и не равны нулю.
alter table student
	alter column name set not null;

alter table student
	add constraint uniq_name unique (name);

-- Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty
	add constraint uniq_faculty unique (name, color);

-- При создании студента без возраста ему автоматически должно присваиваться 20 лет.
insert into student(id, name, faculty_id) values (5,'Sonya', 4);

alter table student
	alter column age set default 20;

select * from student;

-- В корне проекта нужно создать файл scripts421.sql
-- (что значит 4-й курс, 2-й урок, 1-е задание)
-- и поместить в него запросы для создания ограничений.