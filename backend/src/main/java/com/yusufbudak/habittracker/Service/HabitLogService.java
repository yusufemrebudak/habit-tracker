package com.yusufbudak.habittracker.Service;


import com.yusufbudak.habittracker.Request.HabitLogRequestDTO;
import com.yusufbudak.habittracker.Response.HabitLogResponseDTO;
import com.yusufbudak.habittracker.Response.StreakResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface HabitLogService {


    HabitLogResponseDTO upsertLog(Long habitId, HabitLogRequestDTO dto);

    List<HabitLogResponseDTO> getLogsForHabit(Long habitId, LocalDate from, LocalDate to);

    StreakResponseDTO getCurrentStreak(Long habitId);
}
