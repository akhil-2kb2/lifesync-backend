package com.lifesync.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileStorageDTO {
    private MultipartFile file;
}
