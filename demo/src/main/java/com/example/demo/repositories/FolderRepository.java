package com.example.demo.repositories;

import com.example.demo.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
    List<Folder> findAll();
}
