package com.truestore.backend.app;

import java.time.LocalDateTime;

import static com.truestore.backend.user.UserTestData.*;

public class AppTestData {

    public static final String APP_UUID_1 = "app_1_uuid";
    public static final String APP_UUID_2 = "app_2_uuid";
    public static final String APP_UUID_NOT_FOUND = "app_100_uuid";

    public static final App APP_1 = new App(APP_UUID_1, "AppName1", "AppDescription1", USER_1, 100.0, 200.0, true, "", "", LocalDateTime.now());
    public static final App APP_2 = new App(APP_UUID_2, "AppName2", "AppDescription2", USER_2, 100.0, 200.0, true, "", "", LocalDateTime.now());
    public static final AppTo APP_TO_1 = new AppTo(APP_UUID_1, "AppName1", "AppDescription1", USER_1_UUID, 100.0, 200.0, true, "", "");
    public static final AppTo APP_TO_2 = new AppTo(APP_UUID_2, "AppName2", "AppDescription2", USER_2_UUID, 100.0, 200.0, true, "", "");

}
