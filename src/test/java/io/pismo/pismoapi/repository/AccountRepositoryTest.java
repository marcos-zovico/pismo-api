package io.pismo.pismoapi.repository;

import io.pismo.pismoapi.PismoApiApplication;
import io.pismo.pismoapi.PismoApplicationTest;
import io.pismo.pismoapi.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static io.pismo.pismoapi.TestUtil.createAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@PismoApplicationTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    private Account account;
    private String documentNumber;

    @BeforeEach
    public void setup() {
        this.documentNumber = UUID.randomUUID().toString();
        this.account = createAccount(documentNumber);
    }

    @Test
    @DisplayName("Save Account")
    public void saveAccount() {

        repository.save(account);
        Optional<Account> createdAccount = repository.findById(account.getId());

        assertNotNull(createdAccount.get());
        assertEquals(documentNumber, createdAccount.get().getDocumentNumber());
    }

    @Test
    @DisplayName("Find Account by document number")
    public void findByDocumentNumber() {
        repository.save(account);

        Optional<Account> createdAccount = repository.findByDocumentNumber(documentNumber);

        assertNotNull(createdAccount.get());
        assertEquals(documentNumber, createdAccount.get().getDocumentNumber());
    }
}
