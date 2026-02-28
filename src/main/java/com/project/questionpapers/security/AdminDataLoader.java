package com.project.questionpapers.security;

import com.project.questionpapers.user.User;
import com.project.questionpapers.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminDataLoader {

    @Bean
    CommandLineRunner loadAdmins(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            createAdminIfNotExists(
                    "23010453175",
                    "himanshu@11b",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23010453215",
                    "Kshitij@123",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23010453177",
                    "jayash44338w",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23010453245",
                    "festivalhopper@19",
                    userRepository,
                    passwordEncoder
            );
        };
    }

    private void createAdminIfNotExists(
            String collegeId,
            String rawPassword,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        if (userRepository.existsByStudentCollegeId(collegeId)) {
            return;
        }

        User admin = new User();
        admin.setStudentCollegeId(collegeId);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRoles(new HashSet<>(Set.of(Role.ADMIN)));
        admin.setEnabled(true);

        userRepository.save(admin);

    }
}

