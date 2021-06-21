package com.example.security.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credential {
    @Id
    private String accessToken;
    @Column(insertable = false, updatable = false)
    private long accountId ;
    private int status;
    private long createdAtMLS;
    private long expiredAtMLS;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    public boolean isExpired() {
        return this.expiredAtMLS < Calendar.getInstance().getTimeInMillis();
    }

    public static final class CredentialBuilder {
        private String accessToken;
        private int status;
        private long createdAtMLS;
        private long expiredAtMLS;
        private Account account;

        private CredentialBuilder() {
        }

        public static CredentialBuilder aCredential() {
            return new CredentialBuilder();
        }

        public CredentialBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        public CredentialBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public CredentialBuilder withCreatedAtMLS(long createdAtMLS) {
            this.createdAtMLS = createdAtMLS;
            return this;
        }

        public CredentialBuilder withExpiredAtMLS(long expiredAtMLS) {
            this.expiredAtMLS = expiredAtMLS;
            return this;
        }

        public CredentialBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public Credential build() {
            Credential credential = new Credential();
            credential.setAccessToken(accessToken);
            credential.setStatus(status);
            credential.setCreatedAtMLS(createdAtMLS);
            credential.setExpiredAtMLS(expiredAtMLS);
            credential.setAccount(account);
            return credential;
        }
    }
}
