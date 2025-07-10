

package com.practice.training.config;

import com.practice.training.models.Student;
import com.practice.training.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    public DataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;


    }



    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() == 0) {
            InputStream inputStream = new ClassPathResource("data.csv").getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<Student> students = new ArrayList<>();
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
                Student student = new Student();
                student.setFirstName(fields[0]);
                student.setLastName(fields[1]);
                student.setEmail(fields[2]);
                student.setPhoneNumber(fields[3]);
                student.setAddress(fields[4]);
                students.add(student);
            }

            studentRepository.saveAll(students);
            logger.info("Loaded students from CSV.");
        }
    }

}

