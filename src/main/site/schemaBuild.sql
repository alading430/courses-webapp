
-- drop table students_courses;
-- drop table courses;
-- drop table users;
-- drop table profiles;

create table users ( 
	id int not null primary key AUTO_INCREMENT,
	profile int not null,
	username varchar(255) not null,
	password varchar(255) not null,
	name varchar(255) not null, 
	surname varchar(255) not null,
	email varchar(255) not null,
	birth_date date,
	address varchar(255),
	phone varchar(255)	
);

alter table users
add constraint uk_username 
unique (username);

create table profiles ( 
	id int not null primary key AUTO_INCREMENT,
	code varchar(4) not null,
	description varchar(255) not null
);

alter table profiles add index idx_code (code);

alter table users
add constraint fk_profile
foreign key (profile)
references profiles(id);

create table courses ( 
	id int not null primary key AUTO_INCREMENT,
	professor int not null,
	name varchar(255) not null, 
	description varchar(255) not null,
	start_date date not null,
	end_date date,
	credits int
);

alter table courses
add constraint uk_name 
unique (name);

alter table courses add index idx_start_date (start_date);
alter table courses add index idx_end_date (end_date);

alter table courses
add constraint fk_professor
foreign key (professor)
references users(id);

create table students_courses ( 
	student int not null,
	course int not null
);

alter table students_courses 
add constraint pk_students_courses 
primary key (student, course);

alter table students_courses
add constraint fk_sc_student
foreign key (student)
references users(id);

alter table students_courses
add constraint fk_sc_course
foreign key (course)
references courses(id);

-- test data
insert into profiles (code, description) values ('PROF', 'Professor');
insert into profiles (code, description) values ('STUD', 'Student');

insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (1, 'fede', '7d11810cf99c74a1f3fa22c3879ea39d', 'Federico', 'Valeri', 'fede@test.it', '1980-09-30', 'Red Street 11', '3334912342');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'matti', '38fe8951595f01a3c16f3d50ea0bcc53', 'Matteo', 'Valeri', 'matti@test.it', '1982-04-19', 'Green Street 28', '3336123409');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (1, 'john', '098f6bcd4621d373cade4e832627b4f6', 'John', 'McEnroe', 'john@test.it', '1978-10-02', 'Blue Street 12', '3335121529');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'scarlett', '098f6bcd4621d373cade4e832627b4f6', 'Scarlett', 'Johansson', 'scarlett@test.it', '1985-03-03', 'Pink Street 234', '3391703409');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'nicolas', '098f6bcd4621d373cade4e832627b4f6', 'Nicolas', 'Cage', 'nicolas@test.it', '1988-01-14', 'Orange Street 52', '3359123409');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'zoe', '098f6bcd4621d373cade4e832627b4f6', 'Zoe', 'Saldana', 'zoe@test.it', '1987-04-04', 'Violet Street 88', '3355423409');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'robert', '098f6bcd4621d373cade4e832627b4f6', 'Robert', 'DeNiro', 'robert@test.it', '1984-12-04', 'Brown Street 2', '3365423239');
insert into users (profile, username, password, name, surname, email, birth_date, address, phone) values (2, 'tom', '098f6bcd4621d373cade4e832627b4f6', 'Tom', 'Hanks', 'tom@test.it', '1988-02-29', 'Black Street 1234', '3395412239');

insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Java Intro', 'Java Introduction', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (3, 'DBMS Intro', 'Database Introduction', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Webapp Intro', 'Webapp Introduction', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Algorithms 1', 'Algorithms and Data Structures 1', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Mobile Dev.', 'Mobile Development', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (3, 'Javascript', 'Javascript and jQuery', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (3, 'Dist. Sys.', 'Distributed Systems', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Networks 1', 'Computer Networks 1', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (3, 'Algorithms 2', 'Algorithms and Data Structures 2', '2100-01-01', '2014-07-31', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Networks 2', 'Computer Networks 2', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Op. Sis.', 'Operating systems', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (3, 'OOP Intro', 'Object Oriented Programming', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'AI Intro', 'Artificial Intelligence 1', '2014-07-01', '2100-01-011', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Crypto 1', 'Cryptography 1', '2014-07-01', '2100-01-01', null);
insert into courses (professor, name, description, start_date, end_date, credits) values (1, 'Elettr 1', 'Electronic Fundamentals', '2014-07-01', '2100-01-01', null);

insert into students_courses values (2, 1);
insert into students_courses values (2, 2);
insert into students_courses values (2, 3);
insert into students_courses values (2, 5);
insert into students_courses values (2, 9);
insert into students_courses values (4, 1);
insert into students_courses values (4, 2);
insert into students_courses values (4, 3);
insert into students_courses values (5, 1);
insert into students_courses values (5, 2);
insert into students_courses values (5, 3);
insert into students_courses values (6, 1);
insert into students_courses values (6, 2);
insert into students_courses values (6, 9);
insert into students_courses values (4, 9);
insert into students_courses values (5, 9);
insert into students_courses values (7, 9);
