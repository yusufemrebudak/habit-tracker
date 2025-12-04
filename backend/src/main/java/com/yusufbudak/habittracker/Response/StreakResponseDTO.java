package com.yusufbudak.habittracker.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StreakResponseDTO {
    private Long habitId;
    private int streakDays;

    public StreakResponseDTO(Long habitId, int streakDays) {
        this.habitId = habitId;
        this.streakDays = streakDays;
    }
}
