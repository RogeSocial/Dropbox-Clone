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

    public Folder(String name, int owner, Account account) {
        this.folder_name = name;
        this.folder_owner = owner;
        this.account = account;
    }

    @Column(nullable = false)
    public String folder_name;

    @Column(nullable = false)
    public int folder_owner;

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;
}
