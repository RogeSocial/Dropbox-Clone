package com.example.demo.repositories;

import com.example.demo.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
    List<Folder> findAll();

    Optional<Folder> findById(Integer id);

}
