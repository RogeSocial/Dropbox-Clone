package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.FileRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    public File createFile(MultipartFile multipartFile, int folderId, String token) {
        Account account = accountService.getUserByToken(token);
        Folder folder = folderService.getFolderById(folderId);

        if (folder == null || folder.getAccount().getId() != account.getId()) {
            throw new RuntimeException("Folder not found or you don't have permission to upload to this folder");
        }

        System.out.println("Token: " + token);
        System.out.println("Folder ID: " + folderId);
        System.out.println("Account ID: " + account.getId());
        System.out.println("Folder Account ID: " + folder.getAccount().getId());

        String name = multipartFile.getOriginalFilename();

        byte[] file_content;
        try {
            file_content = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file content");
        }

        File file = new File(
                name,
                file_content,
                folder,
                account);

        return fileRepository.save(file);
    }

    public List<FileDto> getFilesByAccountId(int accountId, String token) {
        Account account = accountService.getUserByToken(token);

        if (account == null || account.getId() != accountId) {
            throw new RuntimeException("Invalid account or unauthorized access");
        }

        // In postman file_content is returning as null to make it more readable
        List<File> files = fileRepository.findByAccountId(accountId);
        return files.stream()
                .map(file -> {
                    FileDto fileDto = new FileDto();
                    fileDto.setId(file.getId());
                    fileDto.setName(file.getName());
                    return fileDto;
                })
                .collect(Collectors.toList());
    }

    public void deleteFileById(int fileId, String token) {
        Account account = accountService.getUserByToken(token);
        File file = fileRepository.findById(fileId).orElse(null);

        if (file == null || file.getAccount().getId() != account.getId()) {
            throw new RuntimeException("File not found or you don't have permission to delete this file");
        }

        fileRepository.deleteById(fileId);
    }

    public File getFileById(int fileId, String token) {
        Account account = accountService.getUserByToken(token);
        File file = fileRepository.findById(fileId).orElse(null);

        if (file == null || file.getAccount().getId() != account.getId()) {
            throw new RuntimeException("File not found or you don't have permission to access this file");
        }

        return file;
    }
}