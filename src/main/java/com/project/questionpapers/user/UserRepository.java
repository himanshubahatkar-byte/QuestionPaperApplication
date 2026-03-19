package com.project.questionpapers.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // Used for login & authentication
    Optional<User> findByStudentCollegeId(String studentCollegeId);

    // Used during registration
    boolean existsByStudentCollegeId(String studentCollegeId);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByEmail(String email);
}

