package io.pismo.pismoapi.controller.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotEmpty
    @JsonProperty("transaction_code")
    private String transactionCode;

    @NotNull
    @JsonProperty("account_id")
    private Long accountId;

    @NotNull
    @JsonProperty("operation_type_id")
    private Long operationTypeId;

    @NotNull
    @JsonProperty("amount")
    private BigDecimal amount;
}
