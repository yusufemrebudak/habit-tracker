package com.yusufbudak.habittracker.Response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class HabitLogResponseDTO {

    private Long id;
    private Long habitId;
    private LocalDate logDate;
    private boolean completed;
    private LocalDateTime createdAt;
}
