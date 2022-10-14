package com.truestore.backend.contract;

import com.truestore.backend.app.App;
import com.truestore.backend.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts", uniqueConstraints = {@UniqueConstraint(columnNames = {"app_id", "qa_id"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Contract {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    private App app;
    @ManyToOne
    @JoinColumn(name = "qa_id", nullable = false)
    private User qa;
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
}
