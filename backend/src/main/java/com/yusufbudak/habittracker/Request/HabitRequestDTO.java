package com.yusufbudak.habittracker.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitRequestDTO {

    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @Size(max = 500)
    private String description;

    // opsiyonel: null gelirse dokunmayız / create'te default true
    private Integer targetPerWeek;

    private Boolean active;
}