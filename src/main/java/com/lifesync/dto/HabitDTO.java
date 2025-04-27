package com.lifesync.dto;

import com.lifesync.model.HabitFrequency;
import lombok.Data;

@Data
public class HabitDTO {
    private String habitName;
    private HabitFrequency frequency;
}
