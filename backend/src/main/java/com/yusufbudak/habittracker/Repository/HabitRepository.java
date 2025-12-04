package com.yusufbudak.habittracker.Repository;


import com.yusufbudak.habittracker.Domain.HabitEntity;
import com.yusufbudak.habittracker.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<HabitEntity, Long> {

    List<HabitEntity> findByUserId(Long userId);
    Optional<HabitEntity> findByIdAndUserId(Long id, Long userId);

}