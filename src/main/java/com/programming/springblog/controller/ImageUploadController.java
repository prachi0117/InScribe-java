package com.programming.springblog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {
    
    
        @PostMapping("/image")
        public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
            // Save the file (implementation depends on your storage solution)
            String imageUrl = saveFile(file); // Implement this method
    
            // Return the image URL in response
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);
            return ResponseEntity.ok(response);
        }
    
        private String saveFile(MultipartFile file) {
            // Implement file saving logic (e.g., save to disk, cloud storage, etc.)
            // Example: save to a directory and return the URL
            String fileName = file.getOriginalFilename();
            String filePath = "/images/" + fileName; // Define your file path
            // Save file to disk (implement the logic here)
            // For example: Files.copy(file.getInputStream(), Paths.get(filePath));
            return filePath; // Return the URL where the image is accessible
        }
    }
    


