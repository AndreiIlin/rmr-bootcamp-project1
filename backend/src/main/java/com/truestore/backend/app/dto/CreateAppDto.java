package com.truestore.backend.app.dto;

import com.truestore.backend.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateAppDto {
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
    @NotNull(message = "Feature price can't be null")
    @Min(value = 0, message = "Feature price must be positive")
    @Digits(integer = 10, fraction = 2)
    private Float featurePrice;
    @NotNull(message = "Bug price can't be null")
    @Min(value = 0, message = "Bug price must be positive")
    @Digits(integer = 10, fraction = 2)
    private Float bugPrice;
    @NotNull(message = "Available can't be null")
    private Boolean available;
    @NotBlank(message = "Icon image can't be blank")
    @NotNull(message = "Icon image can't be null")
    @NoHtml
    @Size(min = 1, max = 2048, message = "Max length for URL is 2048 characters")
    private String iconImage;
    @NotBlank(message = "Download link can't be blank")
    @NotNull(message = "Download link can't be null")
    @NoHtml
    @Size(min = 1, max = 2048, message = "Max length for URL is 2048 characters")
    private String downloadLink;
}
