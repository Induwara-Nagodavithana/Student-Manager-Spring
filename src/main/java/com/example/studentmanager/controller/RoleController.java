package com.example.studentmanager.controller;

import com.example.studentmanager.model.Role;
import com.example.studentmanager.model.RoleEnum;
import com.example.studentmanager.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        Role role = roleRepository.findByName(RoleEnum.ROLE_STAFF).orElseGet(() -> {
            Role staff = new Role(RoleEnum.ROLE_STAFF);
            Role moderator = new Role(RoleEnum.ROLE_MODERATOR);
            Role admin = new Role(RoleEnum.ROLE_ADMIN);

            roleRepository.save(staff);
            roleRepository.save(moderator);
            roleRepository.save(admin);

            return staff;
        });
    }

    @Operation(summary = "Get all the role", tags = {"role", "all"})
    @GetMapping("/role")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public List<Role> get() {
        return roleRepository.findAll();
    }

    @Operation(summary = "Get one role by id", tags = {"role", "get"})
    @GetMapping("/role/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Role get(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RuntimeException("Role not found for the id " + id);
        }
    }

    @Operation(summary = "Create a role", tags = {"role", "post"})
    @PostMapping("/role")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Role save(@RequestBody Role roleObj) {
        return roleRepository.save(roleObj);
    }

    @Operation(summary = "Update a role by id", tags = {"role", "put"})
    @PutMapping("/role")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public Role update(@RequestBody Role roleObj) {
        return roleRepository.save(roleObj);
    }

    @Operation(summary = "Delete a role by id", tags = {"role", "delete"})
    @DeleteMapping("/role/{id}")
    @PreAuthorize("hasRole('STAFF')  or hasRole('MODERATOR')  or hasRole('ADMIN')")
    public String delete(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.delete(role.get());
            return "Role is deleted with id " + id;
        } else {
            throw new RuntimeException("Role not found for the id " + id);
        }
    }


}
