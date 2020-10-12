package io.pismo.pismoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.pismoapi.PismoApplicationTest;
import io.pismo.pismoapi.controller.account.AccountRequest;
import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static io.pismo.pismoapi.TestUtil.createAccount;
import static io.pismo.pismoapi.TestUtil.newUuid;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@PismoApplicationTest
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AccountRepository repository;

    @Test
    @DisplayName("Must create a new account")
    void mustCreateAccount() throws Exception {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber(newUuid());

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.ACCOUNT_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.document_number").value(request.getDocumentNumber()))
                .andExpect(jsonPath("$.account_id").isNotEmpty());
    }

    @Test
    @DisplayName("Must Not Create Account With Document Number Null")
    void mustNotCreateAccountWithDocumentNumberNull() throws Exception {
        AccountRequest request = new AccountRequest();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(ApiMapping.ACCOUNT_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].fieldName").value("documentNumber"))
                .andExpect(jsonPath("$.errors[0].message").value("must not be empty"));
    }

    @Test
    @DisplayName("Must get account by id")
    void mustGetAccountById() throws Exception {

        Account account = createAccount(newUuid());
        repository.save(account);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(ApiMapping.ACCOUNT_ID_PATH, account.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document_number").value(account.getDocumentNumber()))
                .andExpect(jsonPath("$.account_id").value(account.getId()));
    }

    @Test
    @DisplayName("Should Not Find Account With Unknown Id")
    void shouldNotFindAccountWithUnknownId() throws Exception {

        Long accountId = new Random().nextLong();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(ApiMapping.ACCOUNT_ID_PATH, accountId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}