package com.truestore.backend.money;

import com.truestore.backend.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "money")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Money {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "amount", nullable = false, precision =  10, scale =  2)
    private Float amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction")
    private TypeTransition typeTransition;
}

