package com.example.studentmanager.repository;

import com.example.studentmanager.model.Role;
import com.example.studentmanager.model.RoleEnum;
import com.example.studentmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
