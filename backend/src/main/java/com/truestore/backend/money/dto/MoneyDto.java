package com.truestore.backend.money.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truestore.backend.money.TypeTransition;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {
    private String id;
    private String userId;
    private Float amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    private TypeTransition typeTransition;
}
