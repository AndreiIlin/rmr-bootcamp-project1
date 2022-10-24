package com.truestore.backend.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truestore.backend.report.ReportStatus;
import com.truestore.backend.report.ReportType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortReportDto {
    private String id;
    private String contractId;
    private String title;
    private ReportType reportType;
    private ReportStatus reportStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
