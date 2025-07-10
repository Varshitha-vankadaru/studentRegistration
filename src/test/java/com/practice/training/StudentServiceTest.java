package com.practice.training;

import com.practice.training.dao.StudentDao;
import com.practice.training.models.Student;
import com.practice.training.repository.StudentRepository;
import com.practice.training.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentService studentService;

	@Test
	 void testRegisterStudent_Success() {
		Student studentEntity = new Student();
		studentEntity.setId(12345678L);
		when( studentRepository.findById(anyLong())).thenReturn(Optional.empty());
		when( studentRepository.save(any())).thenReturn(studentEntity);
		studentService.registerStudent(getStudentDao());
		verify(studentRepository, times(1)).save(any(Student.class));
	}

	public StudentDao getStudentDao(){
		StudentDao student = new StudentDao();
		student.setFirstName("Varshitha");
		student.setLastName("Vankadaru");
		student.setEmail("varna@email.com");
		student.setPhoneNumber("1234567890");
		student.setAddress("Chicago");
		return student;
	}

}
