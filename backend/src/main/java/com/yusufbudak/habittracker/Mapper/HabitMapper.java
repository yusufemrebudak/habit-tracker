package com.yusufbudak.habittracker.Mapper;

import com.yusufbudak.habittracker.Domain.HabitEntity;
import com.yusufbudak.habittracker.Request.HabitRequestDTO;
import com.yusufbudak.habittracker.Response.HabitResponseDTO;

import java.time.LocalDateTime;

public class HabitMapper {

    public static HabitEntity toNewEntity(HabitRequestDTO dto) {
        HabitEntity entity = new HabitEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setTargetPerWeek(dto.getTargetPerWeek());

        // active alanı null ise true olsun (entity @PrePersist’te de true yapıyor zaten)
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        } else {
            entity.setActive(true);
        }

        // createdAt'i entity @PrePersist de set ediyor ama istersen burada da set edebilirsin
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static void updateEntity(HabitEntity entity, HabitRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setTargetPerWeek(dto.getTargetPerWeek());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
        // updatedAt alanın yok, o yüzden dokunmuyoruz
    }

    public static HabitResponseDTO toDto(HabitEntity entity) {
        HabitResponseDTO dto = new HabitResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setTargetPerWeek(entity.getTargetPerWeek());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
