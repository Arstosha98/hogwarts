-- Описание структуры: у каждого человека есть машина.
-- Причем несколько человек могут пользоваться одной машиной.
-- У каждого человека есть имя, возраст и признак того, что у него есть права (или нет).
-- У каждой машины есть марка, модель и стоимость.
-- Также не забудьте добавить таблицам первичные ключи и связать их.

create table car (
	id serial primary key,
	brand varchar,
	model varchar,
	price numeric
);

create table driver (
	id serial primary key,
	name varchar,
	age int,
	driversLicense boolean,
	car_id int references car(id)
);

insert into car values (1, 'BMW', 'X5', 5000000);
insert into car values (2, 'Kia', 'Rio', 1500000);
insert into car values (3, 'Skoda', 'Octavia', 2500000);

insert into driver values (1, 'Anton', 25, true, 1);
insert into driver values (2, 'Tatyana', 24, true, 2);
insert into driver values (3, 'Natalya', 30, true, 2);
insert into driver values (4, 'Olga', 45, true, 3);
insert into driver values (5, 'Andrey', 55, true, 3);

select * from car;
select * from driver;

select d.name as driver_name, c.brand as car_brand, c.model as car_model, c.price as car_price
from driver d
join car c on d.car_id = c.id;

-- All students info.
select s.name as name_student, s.age, f."name" as name_faculty, f.color as color_faculty
from student s
left join faculty f on s.faculty_id = f.id ;

-- Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
-- (достаточно получить только имя и возраст студента) школы Хогвартс вместе
-- с названиями факультетов.
select s.name as name_student, s.age, f."name" as name_faculty
from student s
right join faculty f on s.faculty_id = f.id ;

-- Составить второй JOIN-запрос, чтобы получить только тех студентов,
-- у которых есть аватарки.
select s.name as name_student, s.age, f."name" as name_faculty
from student s
inner join faculty f on s.faculty_id = f.id ;

-- В корне проекта создать файл scripts422.sql и поместить в него запрос.
