package com.truestore.backend.app.dto;

import com.truestore.backend.validation.NoHtml;
import com.truestore.backend.validation.OnCreate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateAppDto {
    @NotBlank(message = "App name can't be blank", groups = OnCreate.class)
    @NotNull(message = "App name can't be null", groups = OnCreate.class)
    @NoHtml
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters", groups = OnCreate.class)
    private String appName;
    @NotBlank(message = "App description can't be blank", groups = OnCreate.class)
    @NotNull(message = "App description can't be null", groups = OnCreate.class)
    @NoHtml
    @Size(min = 1, max = 5000, message = "Description must be between 1 and 5000 characters", groups = OnCreate.class)
    private String appDescription;
    @NotBlank(message = "UserId can't be blank", groups = OnCreate.class)
    @NotNull(message = "UserId can't be null", groups = OnCreate.class)
    @NoHtml
    private String ownerId;
    @NotNull(message = "Feature price can't be null", groups = OnCreate.class)
    private Float featurePrice;
    @NotNull(message = "Bag price can't be null", groups = OnCreate.class)
    private Float bagPrice;
    @NotNull(message = "Available can't be null", groups = OnCreate.class)
    private Boolean available;
    @NotBlank(message = "Icon image can't be blank", groups = OnCreate.class)
    @NotNull(message = "Icon image can't be null", groups = OnCreate.class)
    @NoHtml
    private String iconImage;
    @NotBlank(message = "Download link can't be blank", groups = OnCreate.class)
    @NotNull(message = "Download link can't be null", groups = OnCreate.class)
    @NoHtml
    private String downloadLink;
}
