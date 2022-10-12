package com.truestore.backend.user;

import com.truestore.backend.validation.NoHtml;
import com.truestore.backend.validation.OnCreate;
import com.truestore.backend.validation.OnLogin;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginRequest {
    @Email(message = "Email should be in right format")
    @NoHtml // https://stackoverflow.com/questions/17480809
    @NotBlank(message = "Email can't be blank", groups = {OnCreate.class, OnLogin.class})
    @NotNull(message = "Email can't be null", groups = {OnCreate.class, OnLogin.class})
    private String email;
    @NotBlank(message = "Password can't be blank", groups = {OnCreate.class, OnLogin.class})
    @NotNull(message = "Password can't be null", groups = {OnCreate.class, OnLogin.class})
    @Size(min = 8, max =  30, message = "Password must be between 8 and 30 characters")
    private String password;
    @NotBlank(message = "Role can't be blank", groups = {OnCreate.class, OnLogin.class})
    @NotNull(message = "Role can't be null", groups = {OnCreate.class, OnLogin.class})
    @NoHtml // https://stackoverflow.com/questions/17480809
    private String role;
}
