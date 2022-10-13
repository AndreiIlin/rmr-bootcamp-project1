package com.truestore.backend.contract.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractDto {
    @NotNull(message = "App id can't be null")
    private UUID appId;
}
