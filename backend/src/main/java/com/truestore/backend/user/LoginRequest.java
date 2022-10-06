package com.truestore.backend.user;

import com.truestore.backend.validation.user.NoHtml;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NoHtml // https://stackoverflow.com/questions/17480809
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NoHtml // https://stackoverflow.com/questions/17480809
    private String role;
}
