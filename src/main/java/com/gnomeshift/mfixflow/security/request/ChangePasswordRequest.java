package com.gnomeshift.mfixflow.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~`\"'|\\\\/-]{8,}$")
    @NotBlank
    private String newPassword;
}
