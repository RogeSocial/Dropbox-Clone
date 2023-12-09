package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dtos.FolderDto;
import com.example.demo.models.Account;
import com.example.demo.models.Folder;
import java.util.List;

import com.example.demo.repositories.FolderRepository;

@Service
public class FolderService {

    private FolderRepository folderRepository;
    private AccountService accountService;

    @Autowired
    public FolderService(FolderRepository folderRepository, AccountService accountService) {
        this.folderRepository = folderRepository;
        this.accountService = accountService;
    }

    public Folder createFolder(FolderDto folderDto, String token) {
        Account account = accountService.getUserByToken(token);

        var folder = new Folder(
                folderDto.getName(),
                folderDto.getOwner(), 
                account);
        return this.folderRepository.save(folder);
    }

    public List<Folder> getAllFolders() {
        return this.folderRepository.findAll();
    }
}
