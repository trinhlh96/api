package com.example.security.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String passwordHash;
    private int role; //1 :admin. 2 :user
    private int status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Credential> tokens;

    public String getRoleString() {
        return this.role == 1 ? "ADMIN" : "USER";
    }


    public static final class AccountBuilder {
        private String username;
        private String passwordHash;
        private int role;
        private int status;

        private AccountBuilder() {
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder withPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public AccountBuilder withRole(int role) {
            this.role = role;
            return this;
        }


        public AccountBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.setUsername(username);
            account.setPasswordHash(passwordHash);
            account.setRole(role);
            account.setStatus(status);
            return account;
        }
    }
}

