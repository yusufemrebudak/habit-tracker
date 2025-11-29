package com.yusufbudak.habittracker.Domain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(
        name = "habit_logs",
        uniqueConstraints = {
                // Bir habit için bir günde en fazla bir kayıt olsun:
                @UniqueConstraint(
                        name = "uk_habit_logs_habit_date",
                        columnNames = {"habit_id", "log_date"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi alışkanlığa ait?
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "habit_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_habit_logs_habit")
    )
    private HabitEntity habit;

    // Hangi günün log'u?
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    // O gün yapıldı mı?
    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


}
