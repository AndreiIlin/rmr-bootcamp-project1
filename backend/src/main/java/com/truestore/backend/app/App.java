package com.truestore.backend.app;

import com.truestore.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "app")
@AllArgsConstructor
@NoArgsConstructor
public class App {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name = "name", nullable = false, unique = true)
    private String appName;
    @Column(name = "description", nullable = false, length = 5000)
    private String appDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
    @Column(name = "feature_price", nullable = false)
    private Double featurePrice;
    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;
    @Column(name = "available", nullable = false, columnDefinition = "bool default true")
    private Boolean available = true;
//    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "icon_image", nullable = false)
    private String iconImage;
    @Column(name = "download_link", nullable = false)
    private String downloadLink;
    @Column(name = "created", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created = LocalDateTime.now();

}
