package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int first, int second);

    @Query(value ="SELECT COUNT(*) FROM student",nativeQuery = true)
    int getNumberOfStudents ();

    @Query(value = "SELECT AVG(age) FROM public.student",nativeQuery = true)
    float getAvgStudentAge ();

    @Query(value = "SELECT *\n" +
            "FROM public.student\n" +
            "order by id desc\n" +
            "limit 5",nativeQuery = true)
    List<Student> findLastFiveStudent();
}
