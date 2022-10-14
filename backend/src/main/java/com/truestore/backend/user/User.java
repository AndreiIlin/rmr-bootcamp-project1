package com.truestore.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truestore.backend.validation.ValueOfEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;
    @ValueOfEnum(enumClass = UserRole.class)
    @Column(name = "role")
    private String role;

    public User(String email, String password, String role) {
        this(null, email, password, role);
    }

}
