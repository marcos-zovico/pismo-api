package io.pismo.pismoapi;

import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.OperationType;
import io.pismo.pismoapi.domain.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestUtil {

    private TestUtil(){
    }

    public static Account createAccount(String documentNumber) {
        Account account = new Account();
        account.setDocumentNumber(documentNumber);
        return account;
    }

    public static OperationType createOperationType(String description) {
        OperationType operationType = new OperationType();
        operationType.setDescription(description);
        return operationType;
    }

    public static Transaction createTransaction(Account account, OperationType operationType, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(amount);
        transaction.setEventDate(LocalDateTime.now());
        transaction.setTransactionCode(UUID.randomUUID().toString());
        return transaction;
    }

}
