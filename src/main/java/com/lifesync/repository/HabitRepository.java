package com.lifesync.repository;

import com.lifesync.model.Habit;
import com.lifesync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUser(User user);

    Optional<Habit> findByIdAndUser(Long id, User user);
}
