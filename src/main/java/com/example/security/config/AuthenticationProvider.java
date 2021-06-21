package com.example.security.config;

import com.example.security.entity.Account;
import com.example.security.server.AccountServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static Logger LOGGER = Logger.getLogger(AuthenticationProvider.class.getName());
    @Autowired
    AccountServer accountServer;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        LOGGER.log(Level.SEVERE,"Check AuthenticationProvider");
        Object credential = usernamePasswordAuthenticationToken.getCredentials();
        if (credential == null){
            throw new UsernameNotFoundException("Credential not found");
        }
        String accessToken = String.valueOf(credential);
        LOGGER.log(Level.SEVERE,"accessToken " + accessToken);
        Account account = accountServer.findAccountByToken(accessToken);
        if (account == null){
            throw new UsernameNotFoundException("Cannot find user with authentication token " + accessToken);
        }
        LOGGER.log(Level.SEVERE,"Account " +account.getUsername());

        return User.builder()
                .username(account.getUsername())
                .password(account.getPasswordHash())
                .roles(account.getRoleString())
                .build();
    }
}
