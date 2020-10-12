package io.pismo.pismoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.pismoapi.PismoApplicationTest;
import io.pismo.pismoapi.controller.transaction.TransactionRequest;
import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.OperationType;
import io.pismo.pismoapi.repository.AccountRepository;
import io.pismo.pismoapi.repository.OperationTypeRepository;
import io.pismo.pismoapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Random;

import static io.pismo.pismoapi.TestUtil.*;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PismoApplicationTest
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Account account;
    private OperationType operationType;

    @BeforeEach
    public void setup() {
        account = createAccount(newUuid());
        operationType = createOperationType("PAGAMENTO");

        accountRepository.save(account);
        operationTypeRepository.save(operationType);
    }

    @Test
    @DisplayName("Must Create A New Transaction")
    void mustCreateTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setTransactionCode(newUuid());
        request.setAmount(new BigDecimal("125.50"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.TRANSACTION_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").isNotEmpty())
                .andExpect(jsonPath("$.transaction_code").value(request.getTransactionCode()))
                .andExpect(jsonPath("$.account_id").value(request.getAccountId()))
                .andExpect(jsonPath("$.operation_type_id").value(request.getOperationTypeId()))
                .andExpect(jsonPath("$.amount").value(request.getAmount().doubleValue()))
                .andExpect(jsonPath("$.event_date").isNotEmpty());
    }

    @Test
    @DisplayName("Must Not Create The Same Transaction Twice")
    void mustNotCreateTheSameTransactionTwice() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationType.getId());
        request.setTransactionCode(newUuid());
        request.setAmount(new BigDecimal("125.50"));

        // simulate a request that finish with timeout, but the operation is done on server
        try {
            assertTimeout(ofMillis(10), () -> {
                this.mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiMapping.TRANSACTION_PATH)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
            });
            fail("The request finish with timeout");
        } catch (Throwable e) {
            // ignore, the test must continue
        }

        // call the same request, should not create a new transaction, only return the transaction created on timeout request
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.TRANSACTION_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Must Not Create Transaction With Account Null")
    void mustNotCreateTransactionWithAccountNull() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setOperationTypeId(operationType.getId());
        request.setTransactionCode(newUuid());
        request.setAmount(new BigDecimal("125.50"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.TRANSACTION_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].fieldName").value("accountId"))
                .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
    }

    @Test
    @DisplayName("Must Not Create Transaction With Account that not exist")
    void mustNotCreateTransactionWithAccountThatNotExist() throws Exception {;
        Long accountId = new Random().nextLong();
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(accountId);
        request.setOperationTypeId(operationType.getId());
        request.setTransactionCode(newUuid());
        request.setAmount(new BigDecimal("125.50"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.TRANSACTION_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("account not found with id " + accountId));
    }

    @Test
    @DisplayName("Must Not Create Transaction With Operation Type that not exist")
    void mustNotCreateTransactionWithOperationTypeThatNotExist() throws Exception {;
        Long operationTypeId = new Random().nextLong();
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(account.getId());
        request.setOperationTypeId(operationTypeId);
        request.setTransactionCode(newUuid());
        request.setAmount(new BigDecimal("125.50"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.TRANSACTION_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("operation type not found with id " + operationTypeId));
    }
}