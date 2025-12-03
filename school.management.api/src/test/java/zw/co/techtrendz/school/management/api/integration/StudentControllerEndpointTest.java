package zw.co.techtrendz.school.management.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class StudentControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSaveStudent() throws Exception {
        Person person = new Person();
        person.setFirstName("Endpoint");
        person.setLastName("Test");
        person.setEmail("endpoint@test.com");
        person.setIdentification("ID999999");

        Student student = new Student();
        student.setPerson(person);

        mockMvc.perform(post("/student/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.firstName").value("Endpoint"))
                .andExpect(jsonPath("$.person.lastName").value("Test"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetStudent() throws Exception {
        // This test assumes there's at least one student in the test database
        // In a real scenario, you would set up test data
        mockMvc.perform(get("/student/get/one")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/student/get/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "person.lastName")
                .param("sortDir", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllStudentsByFilter() throws Exception {
        mockMvc.perform(get("/student/get/all/by/filter")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "person.lastName")
                .param("sortDir", "ASC")
                .param("filter", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllStudentViews() throws Exception {
        mockMvc.perform(get("/student/view/get/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "person.lastName")
                .param("sortDir", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllStudentsForDropdown() throws Exception {
        mockMvc.perform(get("/student/dropdown/get/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}