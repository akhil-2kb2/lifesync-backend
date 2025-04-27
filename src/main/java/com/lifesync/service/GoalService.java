package com.lifesync.service;

import com.lifesync.dto.GoalDTO;
import com.lifesync.dto.GoalResponseDTO;
import com.lifesync.model.Goal;
import com.lifesync.model.User;
import com.lifesync.repository.GoalRepository;
import com.lifesync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public GoalResponseDTO createGoal(GoalDTO goalDTO) {
        User user = getCurrentUser();
        Goal goal = Goal.builder()
                .goalTitle(goalDTO.getGoalTitle())
                .description(goalDTO.getDescription())
                .targetDate(goalDTO.getTargetDate())
                .progressPercent(goalDTO.getProgressPercent())
                .user(user)
                .build();
        return mapToResponseDTO(goalRepository.save(goal));
    }

    public List<GoalResponseDTO> getGoals() {
        User user = getCurrentUser();
        return goalRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public GoalResponseDTO updateGoal(Long id, GoalDTO goalDTO) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        goal.setGoalTitle(goalDTO.getGoalTitle());
        goal.setDescription(goalDTO.getDescription());
        goal.setTargetDate(goalDTO.getTargetDate());
        goal.setProgressPercent(goalDTO.getProgressPercent());

        return mapToResponseDTO(goalRepository.save(goal));
    }

    public void deleteGoal(Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        goalRepository.delete(goal);
    }

    private GoalResponseDTO mapToResponseDTO(Goal goal) {
        return GoalResponseDTO.builder()
                .id(goal.getId())
                .goalTitle(goal.getGoalTitle())
                .description(goal.getDescription())
                .targetDate(goal.getTargetDate())
                .progressPercent(goal.getProgressPercent())
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }
}
