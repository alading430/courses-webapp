
select u.username, u.name, u.surname, p.description as profile
from users u, profiles p
where u.profile = p.id;

-- user login
select u.name, p.code 
from users u, profiles p
where u.profile = p.id
and u.username='fede' 
and u.password='7d11810cf99c74a1f3fa22c3879ea39d';

-- popular courses
select c.id, c.name, c.description, CONCAT(u.name, ' ', u.surname) as professor, count(s.student) as idx 
from courses c, users u, students_courses s
where c.professor = u.id
and s.course = c.id
group by s.course
order by idx desc, c.id asc LIMIT 5;

-- professor courses
select id, name, description, other from (
select c.id, c.name, c.description, count(s.student) as other
from courses c, users u, profiles p, students_courses s
where c.professor = u.id
and u.profile = p.id
and s.course = c.id
and u.username = 'mario'
and p.code = 'PROF'
group by s.course
UNION
select c.id, c.name, c.description, 0 as other
from courses c, users u, profiles p
where c.professor = u.id
and u.profile = p.id
and u.username = 'mario'
and p.code = 'PROF'
and c.id not in (select distinct course from students_courses)
) x order by id asc;

-- student courses
select c.id, c.name, c.description, CONCAT(u.name, ' ', u.surname) as other
from courses c, users u, 
(select c.id
from courses c, users u, profiles p, students_courses s
where s.student = u.id
and u.profile = p.id
and s.course = c.id
and u.username = 'matti'
and p.code = 'STUD') x
where c.id = x.id
and c.professor = u.id
order by c.id asc;

-- new courses
select c.id, c.name, c.description, CONCAT(u.name, ' ', u.surname) as other
from courses c, users u 
where c.professor = u.id
and c.id not in 
(select c.id
from courses c, users u, profiles p, students_courses s
where s.student = u.id
and s.course = c.id
and u.profile = p.id
and u.username = 'matti'
and p.code = 'STUD')
order by c.id asc;

-- course students
select u.name, u.surname, u.birth_date, u.address, u.phone, u.email
from students_courses s, users u, profiles p
where s.student = u.id
and u.profile = p.id
and p.code = 'STUD'
and s.course = 
(select id
from courses
where name = 'Java Intro');
