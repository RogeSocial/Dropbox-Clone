package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.util.PasswordEncoderUtil;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.example.demo.dtos.CreateAccountDto;
import com.example.demo.models.Account;
import java.util.List;
import com.example.demo.models.CustomUserDetails;

@Service
public class AccountService {
    
    private AccountRepository accountRepository;
    private PasswordEncoderUtil passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoderUtil passwordEncoderUtil, JwtTokenUtil jwtTokenUtil){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoderUtil;
        this.jwtTokenUtil = jwtTokenUtil;
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

    public String login(String email, String password) {
        var account = accountRepository.findByEmail(email);
        try {
            if (account != null) {
                if (passwordEncoder.verifyPassword(password, account.getPassword(), account.getSalt())) {
                    UserDetails userDetails = new CustomUserDetails(account); // Create UserDetails object with user info
                    String token = jwtTokenUtil.generateToken(userDetails);
                    return "login succesful, your token is :" + token; // Return the token to the caller
                }
            } else {
                return "email not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "wrong password";
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }    

}
