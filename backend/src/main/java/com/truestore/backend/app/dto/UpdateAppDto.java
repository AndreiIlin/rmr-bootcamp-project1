package com.truestore.backend.app.dto;

import com.truestore.backend.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppDto {
    @NoHtml
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String appName;
    @NoHtml
    @Size(min = 1, max = 5000, message = "Description must be between 1 and 5000 characters")
    private String appDescription;
    @Min(value = 0, message = "Feature price must be positive")
    @Digits(integer = 10, fraction = 2)
    private Float featurePrice;
    @Min(value = 0, message = "Bug price must be positive")
    @Digits(integer = 10, fraction = 2)
    private Float bugPrice;
    private Boolean available;
    @NoHtml
    @Size(min = 1, max = 2048, message = "Max length for URL is 2048 characters")
    private String iconImage;
    @NoHtml
    @Size(min = 1, max = 2048, message = "Max length for URL is 2048 characters")
    private String downloadLink;
}
