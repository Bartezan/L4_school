package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping
//    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
//        if (color != null && !color.isBlank()) {
//            return ResponseEntity.ok(facultyService.findByColor(color));
//        }
//        return ResponseEntity.ok(Collections.emptyList());
//    }

    @GetMapping
    public ResponseEntity findByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            name = " ";
            return ResponseEntity.ok(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color));
        }
        if (name != null && !name.isBlank()) {
            color = " ";
            return ResponseEntity.ok(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity findStudentsByFacultyId(@RequestParam Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        Collection<Student> resultStudent = faculty.getStudents();
        return ResponseEntity.ok(resultStudent);
    }

    @GetMapping("/longestNameFaculty")
    public ResponseEntity getLongestFacultyName() {
        return ResponseEntity.ok(facultyService.getLongestFacultyName());
    }

    @GetMapping("/test_stream_endpoint")
    public long executeTestStreamEndpoint() {
        int limit = 1_000_000;
        //Изначальный стрим
        long time1 = System.nanoTime();
        long sum1 = Stream.iterate(1l, a -> a + 1L).limit(limit).reduce(0L, (a, b) -> a + b);
        time1 = System.nanoTime() - time1;

        // 2. Параллельный стрим
        long time2 = System.nanoTime();
        long sum2 = IntStream.rangeClosed(1, limit)
                .parallel()
                .asLongStream()
                .reduce(0, (a, b) -> a + b);
        time2 = System.nanoTime() - time2;

        // 3. Формула Гаусса (самый быстрый)
        long time3 = System.nanoTime();
        long sum3 = (long) limit * (1L + limit) / 2;
        time3 = System.nanoTime() - time3;

        System.out.println("Изначальный стрим:  " + TimeUnit.NANOSECONDS.toMillis(time1) + " ms, sum=" + sum1);
        System.out.println("Паралельный стрим:  " + TimeUnit.NANOSECONDS.toMillis(time2) + " ms, sum=" + sum2);
        System.out.println("Формула Гауса:  " + TimeUnit.NANOSECONDS.toMillis(time3) + " ms, sum=" + sum3);
        return sum3;
    }
}
