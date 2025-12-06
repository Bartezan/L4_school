package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty by id: {}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty by id: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculty by color: {}", color);
        return facultyRepository.findByColor(color);
    }

    public Faculty findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for find faculty by name or color : {}, {}", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public String getLongestFacultyName() {
        String longestName = facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max((s1, s2) -> Integer.compare(s1.length(), s2.length()))
                .orElse("Список факультетов пуст");
        return longestName;
    }


}
