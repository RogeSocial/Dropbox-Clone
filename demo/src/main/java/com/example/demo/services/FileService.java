package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.FileRepository;
import com.example.demo.services.FolderService;
import com.example.demo.services.AccountService;

import com.example.demo.dtos.FileDto;
import com.example.demo.models.Account;
import com.example.demo.models.File;
import com.example.demo.models.Folder;

@Service
public class FileService {

    private FileRepository fileRepository;
    private AccountService accountService;
    private FolderService folderService;

    @Autowired
    public FileService(FileRepository fileRepository, AccountService accountService, FolderService folderService) {
        this.fileRepository = fileRepository;
        this.accountService = accountService;
        this.folderService = folderService;
    }

    public File createFile(FileDto fileDto, int folderId, String token) {
        Account account = accountService.getUserByToken(token);
        Folder folder = folderService.getFolderById(folderId); 

        if (folder == null || folder.getAccount().getId() != account.getId()) {
            throw new RuntimeException("Folder not found or you don't have permission to upload to this folder");
        }

        File file = new File(
                fileDto.getName(),
                fileDto.getLink_to_map(),
                fileDto.getFileContent(),
                folder,
                account
        );

        return fileRepository.save(file);
    }

}
