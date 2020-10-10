package io.pismo.pismoapi.controller.account;

import io.pismo.pismoapi.domain.Account;

public class AccountConverter {

    private AccountConverter() {
    }

    public static AccountResponse toResponse(final Account account) {
        return AccountResponse.builder()
                .accountId(account.getId())
                .documentNumber(account.getDocumentNumber())
                .build();
    }

    public static Account toAccount(final AccountRequest request) {
        final Account account = new Account();
        account.setDocumentNumber(request.getDocumentNumber());
        return account;
    }

}
