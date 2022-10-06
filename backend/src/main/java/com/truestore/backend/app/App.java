package com.truestore.backend.app;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.truestore.backend.user.User;
import com.truestore.backend.validation.user.NoHtml;
import com.truestore.backend.validation.user.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "app")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class App {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @NotBlank(message = "App name can't be blank", groups = OnCreate.class)
    @NotNull(message = "App name can't be null", groups = OnCreate.class)
    @NoHtml
    @Column(name = "name", nullable = false, unique = true)
    private String appName;
    @NotBlank(message = "App description can't be blank", groups = OnCreate.class)
    @NotNull(message = "App description can't be null", groups = OnCreate.class)
    @NoHtml
    @Column(name = "description", nullable = false, length = 500)
    private String appDescription;
    @NotNull(message = "User can't be null", groups = OnCreate.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @NotNull(message = "Feature price can't be null", groups = OnCreate.class)
    @Column(name = "feature_price", nullable = false)
    private Double featurePrice;
    @NotNull(message = "Bag price can't be null", groups = OnCreate.class)
    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;
    @Column(name = "available", nullable = false, columnDefinition = "bool default true")
    private Boolean available = true;
    @NotBlank(message = "Icon image can't be blank", groups = OnCreate.class)
    @NotNull(message = "Icon image can't be null", groups = OnCreate.class)
    @NoHtml
//    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "icon_image", nullable = false)
    private String iconImage;
    @NotBlank(message = "Download link can't be blank", groups = OnCreate.class)
    @NotNull(message = "Download link can't be null", groups = OnCreate.class)
    @NoHtml
    @Column(name = "download_link", nullable = false)
    private String downloadLink;
    @Column(name = "created", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created = LocalDateTime.now();

    public App(String appName, String appDescription, User owner, Double featurePrice, Double bagPrice, String iconImage, String downloadLink) {
        this.appName = appName;
        this.appDescription = appDescription;
        this.owner = owner;
        this.featurePrice = featurePrice;
        this.bagPrice = bagPrice;
        this.iconImage = iconImage;
        this.downloadLink = downloadLink;
    }
}
