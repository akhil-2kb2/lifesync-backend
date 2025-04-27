package com.lifesync.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDTO {
    private String goalTitle;
    private String description;
    private LocalDate targetDate;
    private int progressPercent;  // optional during create
}
