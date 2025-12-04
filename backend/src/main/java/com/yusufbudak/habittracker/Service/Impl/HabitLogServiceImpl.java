package com.yusufbudak.habittracker.Service.Impl;

import com.yusufbudak.habittracker.Domain.HabitEntity;
import com.yusufbudak.habittracker.Domain.HabitLogEntity;
import com.yusufbudak.habittracker.Domain.UserEntity;
import com.yusufbudak.habittracker.Mapper.HabitLogMapper;
import com.yusufbudak.habittracker.Repository.HabitLogRepository;
import com.yusufbudak.habittracker.Repository.HabitRepository;
import com.yusufbudak.habittracker.Repository.UserRepository;

import com.yusufbudak.habittracker.Request.HabitLogRequestDTO;
import com.yusufbudak.habittracker.Response.HabitLogResponseDTO;
import com.yusufbudak.habittracker.Response.StreakResponseDTO;
import com.yusufbudak.habittracker.Service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitLogServiceImpl implements HabitLogService {
    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
    }

    private HabitEntity getHabitForCurrentUser(Long habitId) {
        UserEntity currentUser = getCurrentUser();
        return habitRepository.findByIdAndUserId(habitId, currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Habit not found or not yours"));
    }

    @Override
    public HabitLogResponseDTO upsertLog(Long habitId, HabitLogRequestDTO dto) {
        HabitEntity habit = getHabitForCurrentUser(habitId);

        LocalDate logDate = dto.getLogDate() != null ? dto.getLogDate() : LocalDate.now();

        HabitLogEntity entity = habitLogRepository
                .findByHabitIdAndLogDate(habitId, logDate)
                .orElseGet(() -> {
                    HabitLogEntity newLog = HabitLogMapper.toNewEntity(dto);
                    newLog.setHabit(habit);
                    newLog.setLogDate(logDate); // dto null ise "today"
                    return newLog;
                });

        HabitLogMapper.updateEntity(entity, dto);
        HabitLogEntity saved = habitLogRepository.save(entity);

        return HabitLogMapper.toDto(saved);
    }

    @Override
    public List<HabitLogResponseDTO> getLogsForHabit(Long habitId, LocalDate from, LocalDate to) {
        HabitEntity habit = getHabitForCurrentUser(habitId);

        LocalDate start = from != null ? from : LocalDate.now().minusDays(30);
        LocalDate end = to != null ? to : LocalDate.now();

        List<HabitLogEntity> logs = habitLogRepository
                .findByHabitIdAndLogDateBetweenOrderByLogDateAsc(habit.getId(), start, end);

        return logs.stream()
                .map(HabitLogMapper::toDto)
                .toList();
    }

    @Override
    public StreakResponseDTO getCurrentStreak(Long habitId) {
        HabitEntity habit = getHabitForCurrentUser(habitId);

        LocalDate today = LocalDate.now();
        List<HabitLogEntity> logs = habitLogRepository
                .findByHabitIdAndCompletedIsTrueAndLogDateLessThanEqualOrderByLogDateDesc(
                        habit.getId(), today
                );

        int streak = 0;
        LocalDate expectedDate = today;

        for (HabitLogEntity log : logs) {
            LocalDate logDate = log.getLogDate();

            if (logDate.isEqual(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else if (logDate.isBefore(expectedDate)) {
                // Arada boş gün var, streak burada biter
                break;
            } else {
                // theoretically shouldn't be after, ama güvenli olsun diye:
                continue;
            }
        }

        return new StreakResponseDTO(habit.getId(), streak);
    }

}
