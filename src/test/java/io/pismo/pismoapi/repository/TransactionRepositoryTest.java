package io.pismo.pismoapi.repository;

import io.pismo.pismoapi.PismoApplicationTest;
import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.OperationType;
import io.pismo.pismoapi.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.pismo.pismoapi.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@PismoApplicationTest
class TransactionRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Account account;
    private OperationType operationType;

    @BeforeEach
    public void setup() {
        this.account = createAccount(UUID.randomUUID().toString());
        this.operationType = createOperationType("PAGAMENTO");

        account = accountRepository.save(account);
        operationType = operationTypeRepository.save(operationType);
    }

    @Test
    @DisplayName("Save Transaction")
    void saveTransaction() {

        Transaction transaction = createTransaction(account, operationType, new BigDecimal("125.40"));
        transactionRepository.save(transaction);

        Optional<Transaction> createdTransaction = transactionRepository.findById(transaction.getId());

        assertNotNull(createdTransaction.get());
        assertEquals(new BigDecimal("125.40"), createdTransaction.get().getAmount());
        assertEquals(account.getDocumentNumber(), createdTransaction.get().getAccount().getDocumentNumber());
        assertEquals(operationType.getDescription(), createdTransaction.get().getOperationType().getDescription());
    }

    @Test
    @DisplayName("List Transactions by account")
    void findByAccount() {

        Account anotherAccount = createAccount(UUID.randomUUID().toString());
        accountRepository.save(anotherAccount);

        Transaction transaction1 = createTransaction(account, operationType, new BigDecimal("125.40"));
        Transaction transaction2 = createTransaction(account, operationType, new BigDecimal("32.00"));
        Transaction transaction3 = createTransaction(anotherAccount, operationType, new BigDecimal("12.50"));
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        Pageable sortedByEventDate = PageRequest.of(0, 5, Sort.by("eventDate").descending());

        Page<Transaction> transactions = transactionRepository.listByAccount(account, sortedByEventDate);

        assertEquals(2,transactions.getTotalElements());
        transactions.forEach(t -> {
            assertEquals(account,t.getAccount());
        });
    }

}