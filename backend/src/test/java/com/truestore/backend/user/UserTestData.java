package com.truestore.backend.user;

import java.util.UUID;

public class UserTestData {

    public static final String USER_1_MAIL = "admin@gmail.com";
    public static final String USER_2_MAIL = "user@gmail.com";
    public static final String USER_PASSWORD = "password";
    public static final String VALID_TOKEN = "valid_token";
    public static final String USER_1_UUID = String.valueOf(UUID.randomUUID());
    public static final String USER_2_UUID = String.valueOf(UUID.randomUUID());

    public static final User USER_1 = new User(USER_1_UUID, USER_1_MAIL, USER_PASSWORD, "ROLE_USER");
    public static final User USER_2 = new User(USER_2_UUID, USER_2_MAIL, USER_PASSWORD, "ROLE_USER");
    public static final User USER_2_CHANGED_PASSWORD = new User(USER_2_UUID, USER_2_MAIL, "new_password", "ROLE_USER");

}
