package com.yusufbudak.habittracker.Repository;



import com.yusufbudak.habittracker.Domain.HabitEntity;
import com.yusufbudak.habittracker.Domain.HabitLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitLogRepository extends JpaRepository<HabitLogEntity, Long> {

    List<HabitLogEntity> findByHabitAndLogDateBetween(
            HabitEntity habit,
            LocalDate from,
            LocalDate to
    );

    Optional<HabitLogEntity> findByHabitAndLogDate(HabitEntity habit, LocalDate date);
}