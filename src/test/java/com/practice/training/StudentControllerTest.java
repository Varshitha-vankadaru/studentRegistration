package com.practice.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.training.controller.StudentRegistrationController;
import com.practice.training.dao.StudentDao;
import com.practice.training.models.Student;
import com.practice.training.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRegistrationController.class)
@Import(StudentRegistrationControllerTest.MockServiceConfig.class)
class StudentRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testStudentRegistrationSuccess() throws Exception {
        StudentDao student = new StudentDao();
        student.setFirstName("Alice");
        student.setLastName("Smith");
        student.setEmail("alice@gmail.com");
        student.setPhoneNumber("1234567890");
        student.setAddress("789 Park Avenue");

        when(studentService.registerStudent(any(StudentDao.class))).thenReturn("1");

        mockMvc.perform(post("/student/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }



    @Test
    void testFindStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Alice");
        student.setLastName("Smith");
        student.setEmail("alice@example.com");
        student.setPhoneNumber("1234567890");
        student.setAddress("789 Park Avenue");

        when(studentService.getStudent(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk());
    }


    @Test
    void testFindAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Smith");
        student1.setEmail("alice@example.com");
        student1.setPhoneNumber("1234567890");
        student1.setAddress("789 Park Avenue");

        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("Johnson");
        student2.setEmail("bob@example.com");
        student2.setPhoneNumber("9876543210");
        student2.setAddress("123 Elm Street");

        List<Student> studentList = List.of(student1, student2);

        when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk());

    }




    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Alice");
        student.setLastName("Smith");
        student.setEmail("alice@example.com");
        student.setPhoneNumber("1234567890");
        student.setAddress("789 Park Avenue");

        when(studentService.updateStudent(any(Long.class), any(StudentDao.class)))
                .thenReturn(Optional.of(student));

        mockMvc.perform(put("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }



    @Test
    void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);


        when(studentService.getStudent(1L)).thenReturn(Optional.of(student));
        Mockito.doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
    }




    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }
}







