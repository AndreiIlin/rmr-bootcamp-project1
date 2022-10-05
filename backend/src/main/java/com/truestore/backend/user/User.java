package com.truestore.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truestore.backend.validation.ValueOfEnum;
import com.truestore.backend.validation.user.NoHtml;
import com.truestore.backend.validation.user.OnCreate;
import com.truestore.backend.validation.user.OnLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Email(message = "Email should be in right format")
    @NotBlank(message = "Email can't be blank", groups = {OnCreate.class, OnLogin.class})
    @NotNull(message = "Email can't be null", groups = {OnCreate.class, OnLogin.class})
    @Column(name = "email", nullable = false, unique = true)
    @NoHtml // https://stackoverflow.com/questions/17480809
    private String email;
    @NotBlank(message = "Password can't be blank", groups = {OnCreate.class, OnLogin.class})
    @NotNull(message = "Password can't be null", groups = {OnCreate.class, OnLogin.class})
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;
    @ValueOfEnum(enumClass = UserRole.class)
    @NotBlank(message = "Role can't be blank", groups = OnCreate.class)
    @NotNull(message = "Role can't be null", groups = OnCreate.class)
    @Column(name = "role")
    private String role;

    public User(String email, String password, String role) {
        this(null, email, password, role);
    }

}
