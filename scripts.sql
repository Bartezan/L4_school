-- 1
SELECT * 
FROM student
where age>16 and age <20;
--2
SELECT "name"  
FROM student;
--3
SELECT * 
FROM student
where name like '%о%';
--4
SELECT * 
FROM student
where age < id;
-- 5
SELECT * 
FROM student
order by age;
