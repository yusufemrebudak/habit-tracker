package com.yusufbudak.habittracker.Service.Impl;

import com.yusufbudak.habittracker.Domain.HabitEntity;
import com.yusufbudak.habittracker.Domain.UserEntity;
import com.yusufbudak.habittracker.Mapper.HabitMapper;
import com.yusufbudak.habittracker.Repository.HabitRepository;
import com.yusufbudak.habittracker.Repository.UserRepository;
import com.yusufbudak.habittracker.Request.HabitRequestDTO;
import com.yusufbudak.habittracker.Response.HabitResponseDTO;
import com.yusufbudak.habittracker.Service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
    }
    @Override
    public List<HabitResponseDTO> getMyHabits() {
        UserEntity currentUser = getCurrentUser();
        List<HabitEntity> entities = habitRepository.findByUserId(currentUser.getId());
        return entities.stream()
                .map(HabitMapper::toDto)
                .toList();
    }
    @Override
    public HabitResponseDTO getMyHabit(Long id) {
        UserEntity currentUser = getCurrentUser();
        HabitEntity entity = habitRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Habit not found or not yours"));
        return HabitMapper.toDto(entity);
    }
    @Override
    public HabitResponseDTO createHabit(HabitRequestDTO dto) {
        UserEntity currentUser = getCurrentUser();
        HabitEntity entity = HabitMapper.toNewEntity(dto);
        entity.setUser(currentUser); // ManyToOne
        HabitEntity saved = habitRepository.save(entity);
        return HabitMapper.toDto(saved);
    }
    @Override
    public HabitResponseDTO updateHabit(Long id, HabitRequestDTO dto) {
        UserEntity currentUser = getCurrentUser();
        HabitEntity entity = habitRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Habit not found or not yours"));

        HabitMapper.updateEntity(entity, dto);
        HabitEntity updated = habitRepository.save(entity);
        return HabitMapper.toDto(updated);
    }
    @Override
    public void deleteHabit(Long id) {
        UserEntity currentUser = getCurrentUser();
        HabitEntity entity = habitRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Habit not found or not yours"));
        habitRepository.delete(entity);
    }

}
