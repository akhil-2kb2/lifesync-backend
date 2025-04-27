package com.lifesync.service;

import com.lifesync.dto.HabitDTO;
import com.lifesync.dto.HabitResponseDTO;
import com.lifesync.model.Habit;
import com.lifesync.model.User;
import com.lifesync.repository.HabitRepository;
import com.lifesync.repository.UserRepository;
import com.lifesync.model.HabitFrequency; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public HabitResponseDTO createHabit(HabitDTO habitDTO) {
        User user = getCurrentUser();
        Habit habit = Habit.builder()
                .habitName(habitDTO.getHabitName())
                .frequency(habitDTO.getFrequency())
                .streakCount(0)
                .user(user)
                .build();
        return mapToResponseDTO(habitRepository.save(habit));
    }

    public List<HabitResponseDTO> getHabits() {
        User user = getCurrentUser();
        return habitRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public HabitResponseDTO trackHabit(Long id) {
        User user = getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        LocalDate today = LocalDate.now();

        if (habit.getLastCompletedDate() == null || 
            (habit.getFrequency() == HabitFrequency.DAILY && today.isAfter(habit.getLastCompletedDate())) ||
            (habit.getFrequency() == HabitFrequency.WEEKLY && today.isAfter(habit.getLastCompletedDate().plusWeeks(1)))) {
            habit.setStreakCount(habit.getStreakCount() + 1);
        }

        habit.setLastCompletedDate(today);

        return mapToResponseDTO(habitRepository.save(habit));
    }

    public void deleteHabit(Long id) {
        User user = getCurrentUser();
        Habit habit = habitRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        habitRepository.delete(habit);
    }

    private HabitResponseDTO mapToResponseDTO(Habit habit) {
        return HabitResponseDTO.builder()
                .id(habit.getId())
                .habitName(habit.getHabitName())
                .frequency(habit.getFrequency())
                .streakCount(habit.getStreakCount())
                .lastCompletedDate(habit.getLastCompletedDate())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }
}
