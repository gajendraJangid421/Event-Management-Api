package com.example.event_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    @NotBlank(message = "'username' should not be null or blank")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    @NotBlank(message = "'password' should not be null or blank")
    @Size(min = 8)
    private String password;

    @Column(name = "full_name")
    @NotBlank(message = "'fullName' should not be null or blank")
    private String fullName;

    @Column(name = "email")
    @NotBlank(message = "'email' should not be null or blank")
    private String email;

    @Column(name = "mobile_number")
    @NotBlank(message = "'mobileNumber' should not be null or blank")
    private String mobileNumber;

    @Column(name = "role")
    @NotBlank(message = "'role' should not be null or blank")
    private Role role;

    public void setPassword(String password) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String encryptedPassword = bCrypt.encode(password);

        this.password = encryptedPassword;
    }
}



