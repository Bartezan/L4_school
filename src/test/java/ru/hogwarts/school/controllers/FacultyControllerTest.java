package ru.hogwarts.school.controllers;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.services.FacultyService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Long id = (long) 7;
    private final String color = "blue";
    private final String name = "Test";

    private Faculty testFaculty = new Faculty();

    @Test
    public void testCreateFaculty() throws Exception {
        testFaculty.setId(0);
        testFaculty.setColor(color);
        testFaculty.setName(name);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", testFaculty, String.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultyInfo() throws Exception {
        testFaculty.setId(8);
        testFaculty.setColor(color);
        testFaculty.setName(name);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + 8, String.class))
                .containsIgnoringCase("test")
                .containsIgnoringCase("blue");
    }

    @Test
    public void testEditFaculty() throws Exception {
        testFaculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/" + 8, Faculty.class);
        testFaculty.setName("1234");
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(testFaculty),
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/7",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFindByNameOrColor() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?color=" + "жёлтый", String.class))
                .containsIgnoringCase("жёлтый");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?name=" + "Когтевран", String.class))
                .containsIgnoringCase("Когтевран");
    }

    @Test
    public void testFindStudentsByFacultyId() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/students?id=4", String.class))
                .containsIgnoringCase("Астрид");

    }
}
