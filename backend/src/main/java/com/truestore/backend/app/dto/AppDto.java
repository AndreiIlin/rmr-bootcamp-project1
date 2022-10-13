package com.truestore.backend.app.dto;

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
    @NotBlank(message = "App id can't be blank")
    @NotNull(message = "App id can't be null")
    @NoHtml
    private String id;
    @NotBlank(message = "App name can't be blank")
    @NotNull(message = "App name can't be null")
    @NoHtml
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String appName;
    @NotBlank(message = "App description can't be blank")
    @NotNull(message = "App description can't be null")
    @NoHtml
    @Size(min = 1, max = 5000, message = "Description must be between 1 and 5000 characters")
    private String appDescription;
    @NotBlank(message = "UserId can't be blank")
    @NotNull(message = "UserId can't be null")
    @NoHtml
    private String ownerId;
    @NotNull(message = "Feature price can't be null")
    private Float featurePrice;
    @NotNull(message = "Bug price can't be null")
    private Float bugPrice;
    @NotNull(message = "Available can't be null")
    private Boolean available;
    @NotBlank(message = "Icon image can't be blank")
    @NotNull(message = "Icon image can't be null")
    @NoHtml
    private String iconImage;
    @NotBlank(message = "Download link can't be blank")
    @NotNull(message = "Download link can't be null")
    @NoHtml
    private String downloadLink;
}
