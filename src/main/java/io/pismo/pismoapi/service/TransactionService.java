package io.pismo.pismoapi.service;

import io.pismo.pismoapi.controller.transaction.TransactionRequest;
import io.pismo.pismoapi.domain.Transaction;


public interface TransactionService {

    Transaction createTransaction(final TransactionRequest request);

}
