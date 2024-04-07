package com.example.studentmanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.studentmanager.model.Role;
import com.example.studentmanager.model.RoleEnum;
import com.example.studentmanager.model.User;
import com.example.studentmanager.payload.response.MessageResponse;
import com.example.studentmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        User user = userRepository.findByEmail("admin@gmail.com").orElseGet(() -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            User adminUser = null;
            try {
                Date parsed = format.parse("20110210");
                java.sql.Date dob = new java.sql.Date(parsed.getTime());
                adminUser = new User("admin", "admin@gmail.com", encoder.encode("12345678"), dob);
                adminUser.setRole(new Role(3, RoleEnum.ROLE_ADMIN));
                userRepository.save(adminUser);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return adminUser;
        });
    }

    @Operation(summary = "Get all the user", tags = {"user", "all"})
    @CrossOrigin(origins = "*")
    @GetMapping("/user")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public List<User> get() {
        return userRepository.findAll();
    }

    @Operation(summary = "Get one user by id", tags = {"user", "get"})
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public User get(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found for the id " + id);
        }
    }

    @Operation(summary = "Update a user by id", tags = {"user", "put"})
    @PutMapping("/user")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public User update(@RequestBody User userObj) {
        return userRepository.save(userObj);
    }

    @Operation(summary = "Delete a user by id", tags = {"user", "delete"})
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public String delete(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "User is deleted with id " + id;
        } else {
            throw new RuntimeException("User not found for the id " + id);
        }
    }


}
