package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.dtos.CreateAccountDto;
import com.example.demo.dtos.LoginDto;

import java.util.List;

import com.example.demo.services.AccountService;
import com.example.demo.models.Account;

@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // Retrieve all accounts
    @GetMapping("/account/get-all-users")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Create an account
    @PostMapping("/account/register")
    public ResponseEntity<String> addAccount(@RequestBody CreateAccountDto accountDto){
        accountService.createAccount(accountDto);
        return ResponseEntity.ok("account added: " + accountDto.getName());
    }

    // Login
    @PostMapping("/account/login")
    public ResponseEntity<String> login(@RequestBody LoginDto accountDto){
        return ResponseEntity.ok(accountService.login(accountDto.getEmail(), accountDto.getPassword()));
    }

}

