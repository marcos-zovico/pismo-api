package io.pismo.pismoapi.controller.transaction;

import io.pismo.pismoapi.domain.Transaction;
import io.pismo.pismoapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static io.pismo.pismoapi.controller.ApiMapping.TRANSACTION_PATH;
import static io.pismo.pismoapi.controller.transaction.TransactionConverter.toResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class TransactionController {

    private final TransactionService service;

    @Autowired
    public TransactionController(final TransactionService service) {
        this.service = service;
    }

    @PostMapping(value = TRANSACTION_PATH)
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody final TransactionRequest request) {
        final Transaction transaction = service.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(transaction));
    }
}
