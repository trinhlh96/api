package com.example.security.server;

import com.example.security.entity.Account;
import com.example.security.entity.Credential;
import com.example.security.repository.AccountRepository;
import com.example.security.repository.CredentialRepository;
import com.example.security.util.DataTimeUtil;
import com.example.security.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServer {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //register API
    public Account register(String username, String password, int role, int status) {
        Account account = Account.AccountBuilder.anAccount()
                .withUsername(username)
                .withPasswordHash(passwordEncoder.encode(password))
                .withRole(role)
                .withStatus(status)
                .build();
        return accountRepository.save(account);
    }

    // login API có token
    public Credential login(String username, String password) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsername(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(password, account.getPasswordHash())) {
                Credential credential = Credential.CredentialBuilder.aCredential()
                        .withAccessToken(StringUtil.generateAccessToken())
                        .withCreatedAtMLS(DataTimeUtil.getCurrentTimeInMLS())
                        .withExpiredAtMLS(DataTimeUtil.getTimeAfterDay(10))
                        .withStatus(1)
                        .withAccount(account)
                        .build();
                return credentialRepository.save(credential);
            }
        }
        return null;
    }

    public Account findAccountByToken(String accessToken) {
        Optional<Credential>  optionalCredential = credentialRepository.findById(accessToken);
        if (optionalCredential.isPresent()){
            Credential credential = optionalCredential.get();
            if (credential.isExpired()){
                return null;
            }
            return credential.getAccount();
        }
        return null;
    }

    public Optional<Account> getUser(Long id) {
        return accountRepository.findById(id);
    }
}
