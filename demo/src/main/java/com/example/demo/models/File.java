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

    public File(String name, String link_to_map, byte[] fileContent, Folder folder, Account account) {
        this.name = name;
        this.link_to_map = link_to_map;
        this.fileContent = fileContent;
        this.folder = folder;
        this.account = account;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link_to_map;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "account_id") // Assuming this column exists in your database
    private Account account;
}