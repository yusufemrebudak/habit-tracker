package com.yusufbudak.habittracker.Response;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class HabitResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Integer targetPerWeek;
    private boolean active;
    private LocalDateTime createdAt;
}