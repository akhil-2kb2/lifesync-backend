package com.lifesync.controller;

import com.lifesync.dto.HabitDTO;
import com.lifesync.dto.HabitResponseDTO;
import com.lifesync.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping
    public HabitResponseDTO createHabit(@RequestBody HabitDTO habitDTO) {
        return habitService.createHabit(habitDTO);
    }

    @GetMapping
    public List<HabitResponseDTO> getHabits() {
        return habitService.getHabits();
    }

    @PatchMapping("/{id}/track")
    public HabitResponseDTO trackHabit(@PathVariable Long id) {
        return habitService.trackHabit(id);
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        return "Habit deleted successfully.";
    }
}
