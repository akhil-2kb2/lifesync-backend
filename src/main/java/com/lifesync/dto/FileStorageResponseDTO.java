package com.lifesync.dto;

import com.lifesync.model.StorageProvider;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileStorageResponseDTO {
    private Long id;
    private String fileName;
    private StorageProvider storageProvider;
    private String storageUrl;
    private LocalDateTime uploadDate;
}
