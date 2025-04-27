package com.lifesync.controller;

import com.lifesync.dto.GoalDTO;
import com.lifesync.dto.GoalResponseDTO;
import com.lifesync.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public GoalResponseDTO createGoal(@RequestBody GoalDTO goalDTO) {
        return goalService.createGoal(goalDTO);
    }

    @GetMapping
    public List<GoalResponseDTO> getGoals() {
        return goalService.getGoals();
    }

    @PutMapping("/{id}")
    public GoalResponseDTO updateGoal(@PathVariable Long id, @RequestBody GoalDTO goalDTO) {
        return goalService.updateGoal(id, goalDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "Goal deleted successfully.";
    }
}
