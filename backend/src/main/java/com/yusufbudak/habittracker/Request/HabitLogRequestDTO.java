package com.yusufbudak.habittracker.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class HabitLogRequestDTO {
    // İstersen logDate'i boş gönderip serviste "today" yapabiliriz.
    private LocalDate logDate;

    @NotNull
    private Boolean completed;
}
