package io.pismo.pismoapi.controller.account;

import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static io.pismo.pismoapi.controller.ApiMapping.ACCOUNT_ID_PATH;
import static io.pismo.pismoapi.controller.ApiMapping.ACCOUNT_PATH;
import static io.pismo.pismoapi.controller.account.AccountConverter.toAccount;
import static io.pismo.pismoapi.controller.account.AccountConverter.toResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Log4j2
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountRepository repository;

    @Autowired
    public AccountController(final AccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping(value = ACCOUNT_PATH)
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody final AccountRequest request) {
        log.info("creating account");
        final Account account = repository.save(toAccount(request));
        log.info("Account successful created");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(account));
    }

    @GetMapping(value = ACCOUNT_ID_PATH)
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") final Long accountId) {
        return repository.findById(accountId)
                .map(a -> ok().body(toResponse(a)))
                .orElse(notFound().build());
    }
}
