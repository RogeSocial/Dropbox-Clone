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
    public int id;

    public File(String name, String link_to_map, Folder folder) {
        this.name = name;
        this.link_to_map = link_to_map;
        this.folder = folder;
    }

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String link_to_map;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    public Folder folder;
}
