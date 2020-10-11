package io.pismo.pismoapi.controller.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Long operationTypeId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("event_date")
    private LocalDateTime eventDate;
}
