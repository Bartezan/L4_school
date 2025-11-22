alter table student
ADD CONSTRAINT age_constraint CHECK (age>=16);
--
alter table student
alter COLUMN name SET NOT NULL;
--
alter table student
ADD CONSTRAINT name_unique UNIQUE (name);
--
alter table faculty
ADD CONSTRAINT nameandcolor_unique UNIQUE (name,color);
--
alter table student
alter COLUMN age SET DEFAULT 20;


