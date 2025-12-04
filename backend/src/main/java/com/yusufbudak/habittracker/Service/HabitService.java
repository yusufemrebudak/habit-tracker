package com.yusufbudak.habittracker.Service;

import com.yusufbudak.habittracker.Request.HabitRequestDTO;
import com.yusufbudak.habittracker.Response.HabitResponseDTO;

import java.util.List;

public interface HabitService {
    List<HabitResponseDTO> getMyHabits();

    HabitResponseDTO getMyHabit(Long id);

    HabitResponseDTO createHabit(HabitRequestDTO dto);

    HabitResponseDTO updateHabit(Long id, HabitRequestDTO dto);

    void deleteHabit(Long id);
}
