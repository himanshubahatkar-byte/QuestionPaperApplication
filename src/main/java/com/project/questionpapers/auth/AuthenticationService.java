package com.project.questionpapers.auth;

import com.project.questionpapers.dto.RegisterRequest;
import com.project.questionpapers.security.JwtService;
import com.project.questionpapers.security.Role;
import com.project.questionpapers.user.User;
import com.project.questionpapers.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void registerStudent(RegisterRequest request) {

        User user = new User();

        user.setStudentCollegeId(request.getStudentCollegeId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Automatically assign STUDENT role
        user.setRoles(Set.of(Role.STUDENT));


        userRepository.save(user);
    }


    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByStudentCollegeId(request.getStudentCollegeId())
                .orElseThrow(() -> new RuntimeException("Invalid studentCollegeId"));


        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid password");
        }

        String role = user.getRoles().iterator().next().name();

        String token = jwtService.generateToken(
                user.getUsername(),
                role
        );

        return new AuthenticationResponse(token);
    }
}

