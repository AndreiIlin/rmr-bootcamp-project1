package com.truestore.backend.contract.dto;

import com.truestore.backend.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractDto {
    @NotBlank(message = "App id can't be blank")
    @NotNull(message = "App id can't be null")
    @NoHtml
    private String appId;

}
