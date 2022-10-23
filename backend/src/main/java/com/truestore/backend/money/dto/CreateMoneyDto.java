package com.truestore.backend.money.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateMoneyDto {
    @NotNull(message = "User id can't be null")
    private UUID userId;
    @NotNull(message = "Amount can't be null")
    @Min(value = 0, message = "Amount must be positive")
    @Digits(integer = 10, fraction = 2)
    private Float amount;
}
