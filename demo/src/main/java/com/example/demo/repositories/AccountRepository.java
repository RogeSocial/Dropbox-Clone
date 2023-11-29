package com.example.demo.repositories;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    List<Account> findAll();

    Account findById(int id);

    Account findByEmail(String email);
}
