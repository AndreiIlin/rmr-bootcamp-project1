package com.truestore.backend.app;

import com.truestore.backend.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apps")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class App {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String appName;
    @Column(name = "description", nullable = false, length = 5000)
    private String appDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
    @Column(name = "feature_price", nullable = false, precision =  10, scale =  2)
    private Float featurePrice;
    @Column(name = "bug_price", nullable = false, precision =  10, scale =  2)
    private Float bugPrice;
    @Column(name = "available", nullable = false, columnDefinition = "bool default true")
    private Boolean available = true;
    @Column(name = "icon_image", nullable = false, length = 2048)
    private String iconImage;
    @Column(name = "download_link", nullable = false, length = 2048)
    private String downloadLink;
    @Column(name = "created", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created = LocalDateTime.now();
    private String contractId;
}
