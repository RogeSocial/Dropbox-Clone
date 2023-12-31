package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.services.FileService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.example.demo.models.File;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.dtos.FileDto;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.List;

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

    @GetMapping("/files/get-files/{accountId}")
    public ResponseEntity<List<FileDto>> getFilesByAccount(@PathVariable int accountId,
            @RequestHeader("Authorization") String token) {

        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<FileDto> fileDtos = fileService.getFilesByAccountId(accountId, token.substring(7));
        if (fileDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(fileDtos);
    }

    @DeleteMapping("/files/delete/{fileId}")
    public ResponseEntity<String> deleteFileById(@PathVariable int fileId,
            @RequestHeader("Authorization") String token) {

        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        try {
            fileService.deleteFileById(fileId, token.substring(7));
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            // Log other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file: " + e.getMessage());
        }
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<byte[]> downloadFileById(@PathVariable int fileId,
            @RequestHeader("Authorization") String token,
            HttpServletResponse response) {

        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            File file = fileService.getFileById(fileId, token.substring(7));
            byte[] fileContent = file.getFile_content();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType
                    .parseMediaType(URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(fileContent))));
            headers.setContentDispositionFormData("attachment", file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            // Log other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}