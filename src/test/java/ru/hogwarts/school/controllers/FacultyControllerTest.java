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
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class FacultyControllerTest {

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
    private FacultyController facultyController;

    Long id = 1L;
    String name = "testname";
    String color = "testcolor";

    @Test
    public void getFacultyInfoTest() throws Exception {

        JSONObject testJSONFaclulty = new JSONObject();
        testJSONFaclulty.put("id", id);
        testJSONFaclulty.put("name", name);
        testJSONFaclulty.put("color", color);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testFaculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void createFacultyTest() throws Exception {

        JSONObject testJSONFaclulty = new JSONObject();
        testJSONFaclulty.put("id", id);
        testJSONFaclulty.put("name", name);
        testJSONFaclulty.put("color", color);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(testJSONFaclulty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void editFacultyTest() throws Exception {

        JSONObject testJSONFaclulty = new JSONObject();
        testJSONFaclulty.put("id", id);
        testJSONFaclulty.put("name", name);
        testJSONFaclulty.put("color", color);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(testJSONFaclulty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facultyRepository).deleteById(1L);
    }

    @Test
    public void findByNameOrColorTest() throws Exception {
        JSONObject testJSONFaclulty = new JSONObject();
        testJSONFaclulty.put("id", id);
        testJSONFaclulty.put("name", name);
        testJSONFaclulty.put("color", color);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, " ")).thenReturn(testFaculty);
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(" ", color)).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void findStudentsByFacultyId() throws Exception {
        JSONObject testJSONFaclulty = new JSONObject();
        testJSONFaclulty.put("id", id);
        testJSONFaclulty.put("name", name);
        testJSONFaclulty.put("color", color);

        Faculty testFaculty = new Faculty();
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
