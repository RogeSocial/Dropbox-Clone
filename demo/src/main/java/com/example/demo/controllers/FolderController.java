package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.example.demo.services.FolderService;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.dtos.FolderDto;
import com.example.demo.models.Folder;

@RestController
public class FolderController {
    
    private FolderService FolderService;

    @Autowired
    public FolderController(FolderService FolderService){
        this.FolderService = FolderService;
    }

    // Verify Token endpoint
    @GetMapping("/folder/verify-token")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String token) {

        boolean isValid = JwtTokenUtil.verifyToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired");
        }
    }

    // Create a Folder
    @PostMapping("/folder/create")
    public ResponseEntity<String> createFolder(@RequestBody FolderDto folderDto, @RequestHeader("Authorization") String token) {

        System.out.println("Token: " + token);

            // Remove "Bearer " prefix if present
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " (7 characters)
        }

        boolean isValidToken = JwtTokenUtil.verifyToken(token);
        if (isValidToken) {
            FolderService.createFolder(folderDto, token);
            return ResponseEntity.ok("Folder created: " + folderDto.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    //Gem folder by id
    @GetMapping("/folder/get-folder/{id}")
    public ResponseEntity<Folder> getFolderById(@RequestHeader("Authorization") String token, @PathVariable Integer id){
        return ResponseEntity.ok(FolderService.getFolderById(id));
    }

    //Get all folders only for testing
    @GetMapping("/folder/get-all-folders")
    public ResponseEntity<List<Folder>> getAllFolders(){
        return ResponseEntity.ok(FolderService.getAllFolders());
    }
}
