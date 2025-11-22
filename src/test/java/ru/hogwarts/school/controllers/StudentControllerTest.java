package ru.hogwarts.school.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    Long id = 1L;
    String name = "testname";
    int age = 15;

    @Test
    public void getStudentInfoTest() throws Exception {

        JSONObject testJSONStudent = new JSONObject();
        testJSONStudent.put("id", id);
        testJSONStudent.put("name", name);
        testJSONStudent.put("age", age);

        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    public void createStudentTest() throws Exception {

        JSONObject testJSONStudent = new JSONObject();
        testJSONStudent.put("id", id);
        testJSONStudent.put("name", name);
        testJSONStudent.put("age", age);

        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(testJSONStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    public void editStudentTest() throws Exception {

        JSONObject testJSONStudent = new JSONObject();
        testJSONStudent.put("id", id);
        testJSONStudent.put("name", name);
        testJSONStudent.put("age", age);

        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(testJSONStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    public void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentRepository).deleteById(1L);
    }
    @Test
    public void findStudentsTest() throws Exception {

        JSONObject testJSONStudent = new JSONObject();
        testJSONStudent.put("id", id);
        testJSONStudent.put("name", name);
        testJSONStudent.put("age", age);

        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);

        List<Student> testStudentList = new ArrayList<>();
        testStudentList.add(testStudent);

        when(studentRepository.findByAge(age)).thenReturn(testStudentList);
        when(studentRepository.findByAgeBetween(14,16)).thenReturn(testStudentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?age=0&first=14&second=16")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?age=15&first=0&second=0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }
    @Test
    public void indStudentByIdReturnFacultyTest() throws Exception {
        JSONObject testJSONStudent = new JSONObject();
        testJSONStudent.put("id", id);
        testJSONStudent.put("name", name);
        testJSONStudent.put("age", age);

        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);

        List<Student> testStudentList = new ArrayList<>();
        testStudentList.add(testStudent);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
