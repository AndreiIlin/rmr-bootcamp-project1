package com.truestore.backend.message.dto;

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
public class CreateMessageDto {
    @NotNull(message = "Contract Id can't be null")
    private UUID contractId;
    @NotNull(message = "Text can't be null")
    @NotBlank(message = "Text can't be blank")
    @Size(min = 1, max = 5000, message = "Text should be between 1 and 5000 characters")
    private String text;
}
