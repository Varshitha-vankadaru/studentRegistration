package com.practice.training.controller;

import com.practice.training.dao.StudentDao;
import com.practice.training.models.Student;
import com.practice.training.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentRegistrationController {

    StudentService studentService;

    public StudentRegistrationController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid StudentDao student) {
        log.info("Request to register a new student");
        return new ResponseEntity<>(studentService.registerStudent(student), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        log.info("Request to find student by id: {}", id);
        Optional<Student> student = studentService.getStudent(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        log.info("Request to find all students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }


    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getPaginatedStudent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Student> students = studentService.getAllStudents(PageRequest.of(page, size));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDao student) {
        log.info("Request to update student by id");
        Optional<Student> student1 = studentService.updateStudent(id, student);
        return student1.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable("id") Long id) {
        log.info("Request to delete student with ID: {}", id);
        Optional<Student> student = studentService.getStudent(id);
        if (student.isEmpty()) {
            log.warn("Student with ID {} not found. Cannot delete.", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        studentService.deleteStudent(id);
        log.info("Deleted student with ID: {}", id);
        return ResponseEntity.noContent().build();
    }


}