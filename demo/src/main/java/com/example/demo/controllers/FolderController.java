package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import com.example.demo.services.FolderService;
import com.example.demo.dtos.FolderDto;
import com.example.demo.models.Folder;

@RestController
public class FolderController {
    
    private FolderService FolderService;

    @Autowired
    public FolderController(FolderService FolderService){
        this.FolderService = FolderService;
    }

    //Create a Folder
    @PostMapping("/folder/create")
    public ResponseEntity<String> createFolder(@RequestBody FolderDto FolderDto){
        FolderService.createFolder(FolderDto);
        return ResponseEntity.ok("Folder created: " + FolderDto.getName());
    }

    //Get all folders only for testing
    @GetMapping("/folder/get-all-folders")
    public ResponseEntity<List<Folder>> getAllFolders(){
        return ResponseEntity.ok(FolderService.getAllFolders());
    }
}
