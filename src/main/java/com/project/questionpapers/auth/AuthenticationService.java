package com.project.questionpapers.auth;

import com.project.questionpapers.dto.ForgotPasswordRequest;
import com.project.questionpapers.dto.LoginRequest;
import com.project.questionpapers.dto.LoginResponse;
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

        if(userRepository.existsByStudentCollegeId(request.getStudentCollegeId())){
            throw new RuntimeException("Student already registered");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        if(userRepository.existsByMobileNumber(request.getMobileNumber())){
            throw new RuntimeException("Mobile number already registered");
        }

        User user = new User();

        user.setStudentCollegeId(request.getStudentCollegeId());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setDepartment(request.getDepartment());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(Role.STUDENT));
        user.setEnabled(true);
        userRepository.save(user);

    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByStudentCollegeId(request.getStudentCollegeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.getRoles().contains(Role.valueOf(request.getRole()))) {
            throw new RuntimeException("Invalid role");
        }

        String role = user.getRoles().iterator().next().name();

        String token = jwtService.generateToken(
                user.getStudentCollegeId(),
                role
        );

        return new LoginResponse(
                "Login successful as " + request.getRole(),
                token
        );
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

    public String resetPassword(ForgotPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return "Password updated successfully";
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

