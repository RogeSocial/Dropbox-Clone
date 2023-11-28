package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.PasswordEncoderUtil;
import com.example.demo.dtos.CreateAccountDto;
import com.example.demo.models.Account;
import java.util.List;

@Service
public class AccountService {
    
    private AccountRepository accountRepository;
    private PasswordEncoderUtil passwordEncoder;
    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoderUtil passwordEncoderUtil){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account createAccount(CreateAccountDto accountDto){
        String salt = BCrypt.gensalt();
        var account = new Account(   
            accountDto.getName(),
            accountDto.getEmail(),
            passwordEncoder.encodePassword(accountDto.getPassword(), salt),
                salt);
         return this.accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

}
