package com.lifesync.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class GoalResponseDTO {
    private Long id;
    private String goalTitle;
    private String description;
    private LocalDate targetDate;
    private int progressPercent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
