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

    List<HabitLogEntity> findByHabitIdAndLogDateBetweenOrderByLogDateAsc(
            Long habitId,
            LocalDate start,
            LocalDate end
    );

    List<HabitLogEntity> findByHabitIdAndCompletedIsTrueAndLogDateLessThanOrEqualOrderByLogDateDesc(
            Long habitId,
            LocalDate date
    );

    Optional<HabitLogEntity> findByHabitIdAndLogDate(Long habitId, LocalDate date);

    List<HabitLogEntity> findByHabitIdAndCompletedIsTrueAndLogDateLessThanEqualOrderByLogDateDesc(
            Long habitId,
            LocalDate date
    );
}