package com.truestore.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTo {
    private String email;
    private String id;
}
