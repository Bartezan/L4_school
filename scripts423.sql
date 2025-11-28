select student.name, age,faculty."name"
from  student
inner join faculty on student.faculty_id = faculty.id
---
select student.name, age
from  student
inner join avatar on student.id  = avatar.student_id