package com.lifesync.repository;

import com.lifesync.model.FileStorage;
import com.lifesync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    List<FileStorage> findByUser(User user);
}
