package com.manikesh.restaurant.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StorageService {
    String Store(MultipartFile file, String filename);
    Optional<Resource> loadAsResource(String id);
}
