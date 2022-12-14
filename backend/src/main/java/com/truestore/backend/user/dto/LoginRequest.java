package com.truestore.backend.user.dto;

import com.truestore.backend.validation.NoHtml;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Setter
@Getter
@ToString
public class LoginRequest {
    @Email(message = "Email should be in right format")
    @NoHtml
    @NotBlank(message = "Email can't be blank")
    @NotNull(message = "Email can't be null")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, max =  30, message = "Password must be between 8 and 30 characters")
    private String password;
    @NotBlank(message = "Role can't be blank")
    @NotNull(message = "Role can't be null")
    @NoHtml
    private String role;
}
