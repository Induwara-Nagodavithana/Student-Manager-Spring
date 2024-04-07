package com.example.studentmanager.controller;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Operation(summary = "Get all the student", tags = {"student", "all"})
    @GetMapping("/student")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public List<Student> get() {
        return studentRepository.findAll();
    }

    @Operation(summary = "Get one student by id", tags = {"student", "get"})
    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Student get(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RuntimeException("Student not found for the id " + id);
        }
    }

    @Operation(summary = "Create a student", tags = {"student", "post"})
    @PostMapping("/student")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Student save(@RequestBody Student studentObj) {
        return studentRepository.save(studentObj);
    }

    @Operation(summary = "Update a student by id", tags = {"student", "put"})
    @PutMapping("/student")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Student update(@RequestBody Student studentObj) {
        return studentRepository.save(studentObj);
    }

    @Operation(summary = "Delete a student by id", tags = {"student", "delete"})
    @DeleteMapping("/student/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public String delete(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return "Student is deleted with id " + id;
        } else {
            throw new RuntimeException("Student not found for the id " + id);
        }
    }


}
