package com.yusufbudak.habittracker.Mapper;


import com.yusufbudak.habittracker.Domain.HabitLogEntity;
import com.yusufbudak.habittracker.Request.HabitLogRequestDTO;
import com.yusufbudak.habittracker.Response.HabitLogResponseDTO;

public class HabitLogMapper {

    public static HabitLogEntity toNewEntity(HabitLogRequestDTO dto) {
        HabitLogEntity entity = new HabitLogEntity();
        entity.setLogDate(dto.getLogDate());
        entity.setCompleted(dto.getCompleted() != null && dto.getCompleted());
        // createdAt @PrePersist ile set edilecek
        return entity;
    }

    public static void updateEntity(HabitLogEntity entity, HabitLogRequestDTO dto) {
        if (dto.getLogDate() != null) {
            entity.setLogDate(dto.getLogDate());
        }
        if (dto.getCompleted() != null) {
            entity.setCompleted(dto.getCompleted());
        }
    }

    public static HabitLogResponseDTO toDto(HabitLogEntity entity) {
        HabitLogResponseDTO dto = new HabitLogResponseDTO();
        dto.setId(entity.getId());
        dto.setHabitId(entity.getHabit().getId());
        dto.setLogDate(entity.getLogDate());
        dto.setCompleted(entity.isCompleted());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
