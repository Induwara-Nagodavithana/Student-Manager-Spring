package com.example.studentmanager.repository;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
    Optional<List<User>> findAllByName(String name);
	
}
