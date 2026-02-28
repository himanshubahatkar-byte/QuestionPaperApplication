package com.project.questionpapers.user;
import com.project.questionpapers.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    // ================= GETTERS & SETTERS =================
    // ================= PRIMARY KEY =================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank
    @Pattern(
            regexp = "\\d{11}",
            message = "Student College ID must be exactly 11 digits"
    )
    @Column(unique = true, nullable = false, length = 11)
    private String studentCollegeId;


    // ================= LOGIN FIELDS =================
    @Getter
    @Column(nullable = false)
    private String password;

    // ================= ROLES =================
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    private boolean enabled = true;

    // ================= CONSTRUCTORS =================
    public User() {
    }

    @PrePersist
    public void assignDefaultRole() {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = new HashSet<>();
            this.roles.add(Role.STUDENT);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return studentCollegeId;
    }

    public void setStudentCollegeId(String studentCollegeId) {
        this.studentCollegeId = studentCollegeId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

