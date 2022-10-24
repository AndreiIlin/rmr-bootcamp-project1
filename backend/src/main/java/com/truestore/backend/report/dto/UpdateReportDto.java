package com.truestore.backend.report.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReportDto {
    @NotNull(message = "Report Id can't be null")
    private UUID reportId;
    @NotBlank(message = "Title can't be blank")
    @Size(min = 3, max = 255, message = "Title should be between 3 and 255 characters")
    private String title;
    @NotBlank(message = "Description can't be blank")
    @Size(min = 3, max = 5000, message = "Description should be between 3 and 5000 characters")
    private String description;
}
