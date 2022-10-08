package com.truestore.backend.app.dto;

import com.truestore.backend.validation.user.NoHtml;
import com.truestore.backend.validation.user.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDto {
    private String id;
    @NotBlank(message = "App name can't be blank", groups = OnCreate.class)
    @NotNull(message = "App name can't be null", groups = OnCreate.class)
    @NoHtml
    private String appName;
    @NotBlank(message = "App description can't be blank", groups = OnCreate.class)
    @NotNull(message = "App description can't be null", groups = OnCreate.class)
    @NoHtml
    private String appDescription;
    @NotBlank(message = "UserId can't be blank", groups = OnCreate.class)
    @NotNull(message = "UserId can't be null", groups = OnCreate.class)
    private String ownerId;
    @NotNull(message = "Feature price can't be null", groups = OnCreate.class)
    private Double featurePrice;
    @NotNull(message = "Bag price can't be null", groups = OnCreate.class)
    private Double bagPrice;
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
