package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.services.FileService;
import com.example.demo.util.JwtTokenUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.dtos.FileDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String token) {
        boolean isValid = isValidToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired");
        }
    }

    @PostMapping("/files/create/{folderId}")
    public ResponseEntity<String> createFile(@RequestParam("file_content") MultipartFile file_content,
            @PathVariable int folderId,
            @RequestHeader("Authorization") String token) {

        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        try {
            fileService.createFile(file_content, folderId, token.substring(7)); // Remove "Bearer " (7 characters)
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            // Log other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    private boolean isValidToken(String token) {
        // Replace this with your actual token validation logic
        return token != null && token.startsWith("Bearer ") && JwtTokenUtil.verifyToken(token.substring(7));
    }
}
