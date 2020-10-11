package io.pismo.pismoapi.service;

import io.pismo.pismoapi.controller.transaction.TransactionRequest;
import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.OperationType;
import io.pismo.pismoapi.domain.Transaction;
import io.pismo.pismoapi.exception.EntityNotFoundException;
import io.pismo.pismoapi.repository.AccountRepository;
import io.pismo.pismoapi.repository.OperationTypeRepository;
import io.pismo.pismoapi.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.pismo.pismoapi.controller.transaction.TransactionConverter.toTransaction;
import static java.lang.String.format;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final String TRANSACTION_ALREADY_EXIST = "Transaction already exist with code %s";
    private static final String ACCOUNT_NOT_FOUND = "account not found with id %d";
    private static final String OPERATION_TYPE_NOT_FOUND = "operation type not found with id %d";

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Autowired
    public TransactionServiceImpl(final TransactionRepository transactionRepository,
                                  final AccountRepository accountRepository,
                                  final OperationTypeRepository operationTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    @Override
    public Transaction createTransaction(TransactionRequest request) {
        log.info(format("Creating transaction with code %s", request.getTransactionCode()));

        Optional<Transaction> opTransaction = transactionRepository.findByTransactionCode(request.getTransactionCode());

        if(opTransaction.isPresent()){
            log.warn(format(TRANSACTION_ALREADY_EXIST, request.getTransactionCode()));
            return opTransaction.get();
        }

        Account account = getAndValidateAccount(request.getAccountId());
        OperationType operationType = getAndValidateOperationType(request.getOperationTypeId());

        Transaction transaction = transactionRepository.save(toTransaction(request, account, operationType));

        log.info("Transaction successful created");

        return transaction;
    }

    private Account getAndValidateAccount(final Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(format(ACCOUNT_NOT_FOUND, accountId)));
    }

    private OperationType getAndValidateOperationType(final Long operationTypeId) {
        return operationTypeRepository.findById(operationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(format(OPERATION_TYPE_NOT_FOUND, operationTypeId)));
    }
}
