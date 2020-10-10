package io.pismo.pismoapi.controller.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountRequest {

    @NotEmpty
    @JsonProperty("document_number")
    private String documentNumber;
}
