package com.gharsaathi.auth.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    
    // Dashboard statistics queries
    long countByRole(Role role);
    long countByCreatedAtAfter(LocalDateTime date);
    
    // Admin user management queries
    List<User> findByRole(Role role);
    List<User> findByEnabled(Boolean enabled);
    List<User> findByRoleAndEnabled(Role role, Boolean enabled);
}
