package com.yusufbudak.habittracker.Domain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "habits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi kullanıcıya ait?
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_habits_user")
    )
    private UserEntity user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    // Basit tutalım: hedef haftada kaç kere? (isteğe bağlı alan)
    @Column
    private Integer targetPerWeek;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitLogEntity> logs;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        // default aktif olsun
        if (!this.active) {
            this.active = true;
        }
    }


}
