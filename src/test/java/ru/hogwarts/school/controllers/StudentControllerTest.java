package ru.hogwarts.school.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.StudentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student testStudent = new Student();
    private final Long id = (long) 7;
    private final String name = "TestName";
    private final int age = 17;

    @Test
    public void testCreateStudent() throws Exception {
        testStudent.setName(name);
        testStudent.setAge(age);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", testStudent, String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentInfo() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 102, String.class))
                .containsIgnoringCase("testname")
                .containsIgnoringCase("17");
    }

    @Test
    public void testEditStudent() throws Exception {
        testStudent = restTemplate.getForObject("http://localhost:" + port + "/student/" + 102, Student.class);
        testStudent.setName("1234");
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/102",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testFindStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=0&first=13&second=15", String.class))
                .containsIgnoringCase("селена")
                .containsIgnoringCase("15");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=15&first=0&second=0", String.class))
                .containsIgnoringCase("селена")
                .containsIgnoringCase("15");
    }

    @Test
    public void testFindStudentByIdReturnFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty?id=55", String.class))
                .containsIgnoringCase("гриффиндор")
                .containsIgnoringCase("красный");
    }
}
