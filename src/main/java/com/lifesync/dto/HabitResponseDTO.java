package com.lifesync.dto;

import com.lifesync.model.HabitFrequency;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class HabitResponseDTO {
    private Long id;
    private String habitName;
    private HabitFrequency frequency;
    private int streakCount;
    private LocalDate lastCompletedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
