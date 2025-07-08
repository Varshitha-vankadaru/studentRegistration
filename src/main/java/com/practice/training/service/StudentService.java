package com.practice.training.service;

import com.practice.training.dao.StudentDao;
import com.practice.training.models.Student;
import com.practice.training.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public String registerStudent(StudentDao student){
        try{
            Optional<Student> studentPhoneNumberCheck = studentRepository.findStudentByPhoneNumber(student.getPhoneNumber());
            if(studentPhoneNumberCheck.isPresent())
                throw new IllegalArgumentException("Phone number already exists");
            else {
                Student studentEntity = new Student();
                studentEntity.setFirstName(student.getFirstName());
                studentEntity.setLastName(student.getLastName());
                studentEntity.setEmail(student.getEmail());
                studentEntity.setAddress(student.getAddress());
                studentEntity.setPhoneNumber(student.getPhoneNumber());
                return "StudentId: " + studentRepository.save(studentEntity).getId();
            }
        } catch(Exception e){
            log.error("Error while saving student with phone number: {}, Exception: {} ", student.getPhoneNumber(), e.getMessage());
            return "Error while saving student with phoneNumber: " + student.getPhoneNumber() + ", Exception: " + e.getMessage();
        }
    }

    public Optional<Student> updateStudent(String phoneNumber,Student updatedStudent){
        return studentRepository.findStudentByPhoneNumber(phoneNumber).map(existingStudent ->{
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setAddress(updatedStudent.getAddress());
            existingStudent.setEmail(updatedStudent.getEmail());
            return studentRepository.save(existingStudent);

        });
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(String phoneNumber){
        return studentRepository.findById(phoneNumber);
    }

    public void deleteStudent(String phoneNumber){
        studentRepository.deleteById(phoneNumber);
    }
}
