package com.truestore.backend.app;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppTo {
    private String id;
    private String appName;
    private String appDescription;
    private String ownerId;
    private Double featurePrice;
    private Double bagPrice;
    private Boolean available;
    private String iconImage;
    private String downloadLink;

    public AppTo(App app) {
        this.id = app.getId();
        this.appName = app.getAppName();
        this.appDescription = app.getAppDescription();
        this.ownerId = app.getOwner().getId();
        this.featurePrice = app.getFeaturePrice();
        this.bagPrice = app.getBagPrice();
        this.available = app.getAvailable();
        this.iconImage = app.getIconImage();
        this.downloadLink = app.getDownloadLink();
    }
}
