package com.practice.training.controller;

import com.practice.training.dao.StudentDao;
import com.practice.training.models.Student;
import com.practice.training.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentRegistrationController {
    @Autowired
    StudentService studentService;

    public StudentRegistrationController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid StudentDao student){
        log.info("Request to register a new student");
        return new ResponseEntity<>(studentService.registerStudent(student), HttpStatus.OK);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Student> findStudentByPhoneNumber(@PathVariable String phoneNumber){
        Optional<Student> student=studentService.getStudent(phoneNumber);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    }

    @GetMapping
    public List<Student> findAllStudents(){
        return studentService.getAllStudents();
    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<Student> updateStudent(@PathVariable String phoneNumber, @RequestBody Student student){
        student.setPhoneNumber(phoneNumber);
        Optional<Student> student1=studentService.updateStudent(phoneNumber,student);
        return student1.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<Void> deleteStudentByPhoneNumber(@PathVariable String phoneNumber){
        Optional<Student> student=studentService.getStudent(phoneNumber);
        if(student.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        studentService.deleteStudent(phoneNumber);
        return ResponseEntity.ok().build();
    }


}
