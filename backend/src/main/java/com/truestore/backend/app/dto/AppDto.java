package com.truestore.backend.app.dto;

import com.truestore.backend.validation.OnUpdate;
import com.truestore.backend.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppDto {
    @NotBlank(message = "App id can't be blank", groups = OnUpdate.class)
    @NotNull(message = "App id can't be null", groups = OnUpdate.class)
    @NoHtml
    private String id;
    @NotBlank(message = "App name can't be blank", groups = OnUpdate.class)
    @NotNull(message = "App name can't be null", groups = OnUpdate.class)
    @NoHtml
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters", groups = OnUpdate.class)
    private String appName;
    @NotBlank(message = "App description can't be blank", groups = OnUpdate.class)
    @NotNull(message = "App description can't be null", groups = OnUpdate.class)
    @NoHtml
    @Size(min = 1, max = 5000, message = "Description must be between 1 and 5000 characters", groups = OnUpdate.class)
    private String appDescription;
    @NotBlank(message = "UserId can't be blank", groups = OnUpdate.class)
    @NotNull(message = "UserId can't be null", groups = OnUpdate.class)
    @NoHtml
    private String ownerId;
    @NotNull(message = "Feature price can't be null", groups = OnUpdate.class)
    private Float featurePrice;
    @NotNull(message = "Bug price can't be null", groups = OnUpdate.class)
    private Float bugPrice;
    @NotNull(message = "Available can't be null", groups = OnUpdate.class)
    private Boolean available;
    @NotBlank(message = "Icon image can't be blank", groups = OnUpdate.class)
    @NotNull(message = "Icon image can't be null", groups = OnUpdate.class)
    @NoHtml
    private String iconImage;
    @NotBlank(message = "Download link can't be blank", groups = OnUpdate.class)
    @NotNull(message = "Download link can't be null", groups = OnUpdate.class)
    @NoHtml
    private String downloadLink;
}
