package com.lifesync.dto;

import com.lifesync.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
