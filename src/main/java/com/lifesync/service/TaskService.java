package com.lifesync.service;

import com.lifesync.dto.TaskDTO;
import com.lifesync.dto.TaskResponseDTO;
import com.lifesync.model.Task;
import com.lifesync.model.TaskStatus;
import com.lifesync.model.User;
import com.lifesync.repository.TaskRepository;
import com.lifesync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TaskResponseDTO createTask(TaskDTO taskDTO) {
        User user = getCurrentUser();
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dueDate(taskDTO.getDueDate())
                .status(TaskStatus.PENDING)
                .user(user)
                .build();
        return mapToResponseDTO(taskRepository.save(task));
    }

    public List<TaskResponseDTO> getTasks() {
        User user = getCurrentUser();
        return taskRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(taskDTO.getStatus());

        return mapToResponseDTO(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
    return TaskResponseDTO.builder()
            .id(task.getId())
            .title(task.getTitle())
            .description(task.getDescription())
            .dueDate(task.getDueDate())
            .status(task.getStatus())
            .createdAt(task.getCreatedAt().atOffset(ZoneOffset.ofHoursMinutes(5, 30))) // +05:30 IST
            .updatedAt(task.getUpdatedAt().atOffset(ZoneOffset.ofHoursMinutes(5, 30)))
            .build();
}

}
