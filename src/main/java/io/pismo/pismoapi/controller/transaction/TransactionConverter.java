package io.pismo.pismoapi.controller.transaction;

import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.OperationType;
import io.pismo.pismoapi.domain.Transaction;

import java.time.LocalDateTime;

public class TransactionConverter {

    private TransactionConverter() {
    }

    public static TransactionResponse toResponse(final Transaction transaction) {
        return TransactionResponse.builder()
                .accountId(transaction.getAccount().getId())
                .operationTypeId(transaction.getOperationType().getId())
                .amount(transaction.getAmount())
                .transactionCode(transaction.getTransactionCode())
                .transactionId(transaction.getId())
                .eventDate(transaction.getEventDate())
                .build();

    }

    public static Transaction toTransaction(final TransactionRequest request, final Account account, final OperationType operationType) {
        final Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setTransactionCode(request.getTransactionCode());
        transaction.setAmount(request.getAmount());
        transaction.setEventDate(LocalDateTime.now());
        return transaction;
    }
}
