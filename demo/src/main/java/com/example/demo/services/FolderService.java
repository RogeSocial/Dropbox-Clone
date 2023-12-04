package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dtos.FolderDto;
import com.example.demo.models.Folder;

import com.example.demo.repositories.FolderRepository;

@Service
public class FolderService {
    
    private FolderRepository folderRepository;
    @Autowired
    public FolderService(FolderRepository folderRepository){
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(FolderDto folderDto){
        var folder = new Folder(
            folderDto.getName(),
            folderDto.getOwner()
        );
        return this.folderRepository.save(folder);
    }
}
