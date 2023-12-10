package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public File(String name, byte[] file_content, Folder folder, Account account) {
        this.name = name;
        this.file_content = file_content;
        this.folder = folder;
        this.account = account;
    }

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] file_content;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}