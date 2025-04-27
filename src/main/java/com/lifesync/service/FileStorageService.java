package com.lifesync.service;

import com.lifesync.dto.FileStorageResponseDTO;
import com.lifesync.model.FileStorage;
import com.lifesync.model.StorageProvider;
import com.lifesync.model.User;
import com.lifesync.repository.FileStorageRepository;
import com.lifesync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDirectory = "uploads";

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public FileStorageResponseDTO uploadFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty. Please select a valid file.");
        }

        User user = getCurrentUser();

        try {
            File dir = new File(uploadDirectory);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create upload directory.");
                }
            }

            String filePath = uploadDirectory + "/" + multipartFile.getOriginalFilename();
            File file = new File(filePath);
            multipartFile.transferTo(file);

            FileStorage fileStorage = FileStorage.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .storageProvider(StorageProvider.LOCAL)
                    .storageUrl(filePath)
                    .user(user)
                    .build();

            return mapToResponseDTO(fileStorageRepository.save(fileStorage));
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    public List<FileStorageResponseDTO> getFiles() {
        User user = getCurrentUser();
        return fileStorageRepository.findByUser(user)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteFile(Long id) {
        User user = getCurrentUser();
        FileStorage fileStorage = fileStorageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        File file = new File(fileStorage.getStorageUrl());
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Failed to delete file from server.");
            }
        }

        fileStorageRepository.delete(fileStorage);
    }

    private FileStorageResponseDTO mapToResponseDTO(FileStorage fileStorage) {
        return FileStorageResponseDTO.builder()
                .id(fileStorage.getId())
                .fileName(fileStorage.getFileName())
                .storageProvider(fileStorage.getStorageProvider())
                .storageUrl(fileStorage.getStorageUrl())
                .uploadDate(fileStorage.getUploadDate())
                .build();
    }
}
