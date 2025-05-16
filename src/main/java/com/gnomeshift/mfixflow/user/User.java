package com.gnomeshift.mfixflow.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gnomeshift.mfixflow.fixRequest.FixRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Email
    @NotNull
    @Size(max = 100)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~`\"'|\\\\/-]{8,}$")
    @NotNull
    private String password;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "masterReference")
    private List<FixRequest> fixRequests;
}
