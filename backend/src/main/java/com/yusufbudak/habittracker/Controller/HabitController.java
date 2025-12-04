package com.yusufbudak.habittracker.Controller;
import com.yusufbudak.habittracker.Request.HabitRequestDTO;
import com.yusufbudak.habittracker.Response.HabitResponseDTO;
import com.yusufbudak.habittracker.Service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @GetMapping
    public ResponseEntity<List<HabitResponseDTO>> getMyHabits() {
        return ResponseEntity.ok(habitService.getMyHabits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitResponseDTO> getHabit(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.getMyHabit(id));
    }

    @PostMapping
    public ResponseEntity<HabitResponseDTO> createHabit(@Valid @RequestBody HabitRequestDTO dto) {
        HabitResponseDTO created = habitService.createHabit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponseDTO> updateHabit(@PathVariable Long id,
                                                        @Valid @RequestBody HabitRequestDTO dto) {
        HabitResponseDTO updated = habitService.updateHabit(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }
}
