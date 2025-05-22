package com.gnomeshift.mfixflow.user;

import com.gnomeshift.mfixflow.fixRequest.FixRequest;
import com.gnomeshift.mfixflow.user.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private long id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    private List<FixRequest> fixRequests;
    private Set<Role> roles;
}
