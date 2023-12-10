package com.example.demo.repositories;

import com.example.demo.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAll();

    List<File> findByAccountId(int accountId);
}
