package com.truestore.backend.app.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppDto {
    private String id;
    private String appName;
    private String appDescription;
    private String ownerId;
    private Float featurePrice;
    private Float bugPrice;
    private Boolean available;
    private String iconImage;
    private String downloadLink;
    private String contractId;
}
