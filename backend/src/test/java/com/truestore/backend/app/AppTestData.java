package com.truestore.backend.app;

import com.truestore.backend.app.dto.AppDto;
import com.truestore.backend.app.dto.CreateAppDto;

import java.time.LocalDateTime;

import static com.truestore.backend.user.UserTestData.*;

public class AppTestData {

    public static final String APP_UUID_1 = "app_1_uuid";
    public static final String APP_UUID_2 = "app_2_uuid";
    public static final String APP_UUID_NOT_FOUND = "app_100_uuid";

    public static final App APP_1 = new App(APP_UUID_1, "AppName1", "AppDescription1", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now());
    public static final App APP_2 = new App(APP_UUID_2, "AppName2", "AppDescription2", USER_2, 100F, 200F, true, "icon", "link", LocalDateTime.now());
    public static final App APP_NEW_WITHOUT_ID = new App(null,"AppNameNew", "AppDescriptionNew", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now());
    public static final App APP_NEW_WITH_ID = new App("NEW", "AppNameNew", "AppDescriptionNew", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now());
    public static final AppDto APP_DTO_1 = new AppDto(APP_UUID_1, "AppName1", "AppDescription1", USER_1_UUID, 100F, 200F, true, "icon", "link");
    public static final AppDto APP_DTO_2 = new AppDto(APP_UUID_2, "AppName2", "AppDescription2", USER_2_UUID, 100F, 200F, true, "icon", "link");
    public static final CreateAppDto CREATE_APP_DTO = new CreateAppDto("AppNameNew", "AppDescriptionNew", USER_1_UUID, 100F, 200F, true, "icon", "link");


}
