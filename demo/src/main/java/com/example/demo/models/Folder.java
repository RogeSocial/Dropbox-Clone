package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public Folder(String name, Account account) {
        this.folder_name = name;
        this.account = account;
    }

    @Column(nullable = false)
    public String folder_name;

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;
}
