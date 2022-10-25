package com.truestore.backend.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truestore.backend.user.dto.UserTo;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FullMessageDto {
    private String id;
    private String contractId;
    private UserTo author;
    private UserTo recipient;
    private String text;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
