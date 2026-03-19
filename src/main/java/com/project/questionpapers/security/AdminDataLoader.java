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
                    "23BTET1087",
                    "Himanshu Bahatkar",
                    "himanshubahatkar@gmail.com",
                    "9356421851",
                    "Electronics & Telecommunication Engineering",
                    "himanshu@51",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23BTET1089",
                    "Jayash Bhuyar",
                    "jayashbhuyar15@gmail.com",
                    "7038677471",
                    "Electronics & Telecommunication Engineering",
                    "jayash44338w",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23BTET1093",
                    "Kshitij Rajendra Taywade",
                    "shitutaywade345@gmail.com",
                    "7038677471",
                    "Electronics & Telecommunication Engineering",
                    "Kshitij345T",
                    userRepository,
                    passwordEncoder
            );

            createAdminIfNotExists(
                    "23BTET1114",
                    "Shivam Satish Deshmukh",
                    "deshmukhshivam001@gmail.com",
                    "9325286317",
                    "Electronics & Telecommunication Engineering",
                    "Shivam@1234",
                    userRepository,
                    passwordEncoder
            );
        };
    }

    private void createAdminIfNotExists(
            String collegeId,
            String fullName,
            String email,
            String mobileNumber,
            String department,
            String rawPassword,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        if (userRepository.existsByStudentCollegeId(collegeId)) {
            return;
        }

        User admin = new User();
        admin.setStudentCollegeId(collegeId);
        admin.setFullName(fullName);
        admin.setEmail(email);
        admin.setMobileNumber(mobileNumber);
        admin.setDepartment(department);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRoles(new HashSet<>(Set.of(Role.ADMIN)));
        admin.setEnabled(true);

        userRepository.save(admin);

    }
}

