package com.practice.training.repository;

import com.practice.training.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
   Optional<Student> findByPhoneNumber(String phoneNumber);
   Page<Student> findAll(Pageable pageable);

}
