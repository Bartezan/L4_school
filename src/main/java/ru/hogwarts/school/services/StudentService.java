package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student by id: {}", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student by id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find student by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int first, int second) {
        logger.info("Was invoked method for find student by age between values {},{}", first, second);
        return studentRepository.findByAgeBetween(first, second);
    }

    public int getNumberOfStudents() {
        logger.info("Was invoked method for get number of student");
        return studentRepository.getNumberOfStudents();
    }

    public float getAvgStudentAge() {
        logger.info("Was invoked method for get avg student age");
        return studentRepository.getAvgStudentAge();
    }

    public Collection<Student> findLastFiveStudent() {
        logger.info("Was invoked method for find last 5 add student");
        return studentRepository.findLastFiveStudent();
    }

    public Collection<String> findAllWhoNameStartA() {
        Collection<String> result = studentRepository.findAll().stream()
                .filter(s -> s.getName().startsWith("А"))
                .sorted(Comparator.comparing(Student::getName))
                .map(Student::getName)
                .peek(System.out::println)
                .toList();
        return result;
    }

    public double getAvgAgeByStream() {
        double avg = studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
        return avg;
    }

    public void getAllStudentParallel() {
        List<String> result = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();

        System.out.println(result.get(0));
        System.out.println(result.get(1));
        new Thread(() -> {
            System.out.println(result.get(2));
            System.out.println(result.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(result.get(4));
            System.out.println(result.get(5));
        }).start();
    }

    public void getAllStudentSynchro() {
        List<String> result = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();
        doPrintSynhro(result.get(0));
        doPrintSynhro(result.get(1));

        new Thread(() -> {
            doPrintSynhro(result.get(2));
            doPrintSynhro(result.get(3));
        }).start();

        new Thread(() -> {
            doPrintSynhro(result.get(4));
            doPrintSynhro(result.get(5));
        }).start();
    }

    public synchronized void doPrintSynhro(String name) {
        System.out.println(name);
    }
}
