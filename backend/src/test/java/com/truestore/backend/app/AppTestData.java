package com.truestore.backend.app;

import com.truestore.backend.app.dto.AppDto;
import com.truestore.backend.app.dto.CreateAppDto;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.truestore.backend.user.UserTestData.*;

public class AppTestData {

    public static final String APP_UUID_1 = String.valueOf(UUID.randomUUID());
    public static final String APP_UUID_2 = String.valueOf(UUID.randomUUID());
    public static final String APP_UUID_NOT_FOUND = String.valueOf(UUID.randomUUID());
    public static final App APP_1 = new App(APP_UUID_1, "AppName1", "AppDescription1", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now(), null);
    public static final App APP_2 = new App(APP_UUID_2, "AppName2", "AppDescription2", USER_2, 100F, 200F, true, "icon", "link", LocalDateTime.now(), null);
    public static final App APP_NEW_WITHOUT_ID = new App(null,"AppNameNew", "AppDescriptionNew", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now(),null);
    public static final App APP_NEW_WITH_ID = new App("NEW", "AppNameNew", "AppDescriptionNew", USER_1, 100F, 200F, true, "icon", "link", LocalDateTime.now(),null);
    public static final AppDto APP_DTO_1 = new AppDto(APP_UUID_1, "AppName1", "AppDescription1", USER_1_UUID, 100F, 200F, true, "icon", "link", null);
    public static final CreateAppDto CREATE_APP_DTO = new CreateAppDto("AppNameNew", "AppDescriptionNew", 100F, 200F, true, "icon", "link");
    public static final CreateAppDto INVALID_APP_DTO = new CreateAppDto("AppName", "AppDescription", 0F, 0F, true, "icon", "link");


}
