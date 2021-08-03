package online.switcheroos.accounts.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.switcheroos.accounts.api.v1.dto.AccountDto;
import online.switcheroos.accounts.api.v1.dto.NewAccountDto;
import online.switcheroos.accounts.api.v1.repository.AccountRepository;
import online.switcheroos.accounts.api.v1.service.AccountService;
import online.switcheroos.accounts.model.Platform;
import online.switcheroos.accounts.model.PlatformAccount;
import online.switcheroos.accounts.model.Role;
import online.switcheroos.accounts.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class AccountControllerImplTest {

    private AccountDto accountDto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        this.accountDto = AccountDto.builder()
                .id(1L)
                .username("testing")
                .email("testing@switcheroos.online")
                .profilePicture("https://switcheroos.online")
                .roles(Set.of(Role.USER))
                .platformAccounts(Set.of(new PlatformAccount(Platform.PSN, "switcheroos")))
                .status(Status.ACTIVE)
                .verified(false)
                .build();
    }

    @Test
    void getAccountByIdShouldReturnAccount() throws Exception {
        String expectedResponse = this.objectMapper.writeValueAsString(accountDto);

        given(accountService.findAccountById(1L)).willReturn(accountDto);

        this.mockMvc.perform(get("/v1/accounts/{id}", this.accountDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(jsonPath("$.id", is(this.accountDto.getId().intValue())))
                .andDo(print());
    }

    @Test
    void getAccountByUsernameShouldReturnAccount() throws Exception {
        String expectedResponse = this.objectMapper.writeValueAsString(accountDto);

        given(accountService.findAccountByUsername("testing")).willReturn(accountDto);

        this.mockMvc.perform(get("/v1/accounts/username/{username}", accountDto.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(jsonPath("$.username", is(this.accountDto.getUsername())))
                .andDo(print());
    }

    @Test
    void getAccountByEmailShouldReturnAccount() throws Exception {
        String expectedResponse = this.objectMapper.writeValueAsString(accountDto);

        given(accountService.findAccountByEmail("testing@switcheroos.online")).willReturn(accountDto);

        this.mockMvc.perform(get("/v1/accounts/email/{email}", accountDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(jsonPath("$.email", is(this.accountDto.getEmail())))
                .andDo(print());
    }

    @Test
    void postAccountShouldReturnNewAccount() throws Exception {
        NewAccountDto newAccountDto =
                new NewAccountDto("testing", "PassW0rd1!", "switcheroos@switcheroos.online");
        this.accountDto.setPlatformAccounts(null);
        this.accountDto.setProfilePicture(null);
        String request = this.objectMapper.writeValueAsString(newAccountDto);
        String expectedResponse = this.objectMapper.writeValueAsString(this.accountDto);

        when(accountService.createAccount(any(NewAccountDto.class))).thenReturn(accountDto);

        this.mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponse))
                .andDo(print());
    }
}