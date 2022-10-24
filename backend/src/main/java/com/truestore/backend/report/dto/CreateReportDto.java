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
public class CreateReportDto {
    @NotNull(message = "Contract Id can't be null")
    private UUID contractId;
    @NotNull(message = "Title can't be null")
    @NotBlank(message = "Title can't be blank")
    @Size(min = 3, max = 255, message = "Title should be between 3 and 255 characters")
    private String title;
    @NotNull(message = "Description can't be null")
    @NotBlank(message = "Description can't be blank")
    @Size(min = 3, max = 5000, message = "Description should be between 3 and 5000 characters")
    private String description;
}
