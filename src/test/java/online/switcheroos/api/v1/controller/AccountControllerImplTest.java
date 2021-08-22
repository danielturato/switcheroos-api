package online.switcheroos.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.dto.ResourceResponseDto;
import online.switcheroos.api.v1.repository.AccountRepository;
import online.switcheroos.api.v1.service.AccountService;
import online.switcheroos.model.Platform;
import online.switcheroos.model.PlatformAccount;
import online.switcheroos.model.Role;
import online.switcheroos.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .id(UUID.randomUUID())
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

        given(accountService.findAccountDtoById(accountDto.getId())).willReturn(accountDto);

        this.mockMvc.perform(get("/v1/accounts/{id}", this.accountDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(jsonPath("$.id", is(this.accountDto.getId().toString())))
                .andDo(print());
    }

    @Test
    void getAccountByUsernameShouldReturnAccount() throws Exception {
        String expectedResponse = this.objectMapper.writeValueAsString(accountDto);

        given(accountService.findAccountDtoByUsername("testing")).willReturn(accountDto);

        this.mockMvc.perform(get("/v1/accounts/username/{username}", accountDto.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andExpect(jsonPath("$.username", is(this.accountDto.getUsername())))
                .andDo(print());
    }

    @Test
    void getAccountByEmailShouldReturnAccount() throws Exception {
        String expectedResponse = this.objectMapper.writeValueAsString(accountDto);

        given(accountService.findAccountDtoByEmail("testing@switcheroos.online")).willReturn(accountDto);

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
        ResourceResponseDto resourceResponseDto = new ResourceResponseDto("/api/v1/accounts/" + accountDto.getId());

        this.accountDto.setPlatformAccounts(null);
        this.accountDto.setProfilePicture(null);
        String request = this.objectMapper.writeValueAsString(newAccountDto);
        String expectedResponse = this.objectMapper.writeValueAsString(resourceResponseDto);

        when(accountService.createAccount(any(NewAccountDto.class))).thenReturn(accountDto);

        this.mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponse))
                .andDo(print());
    }
//
//    @Test
//    void putAccountShouldUpdateAccount() throws Exception {
//        this.accountDto.setUsername("testing-new-user");
//        String requestBody = this.objectMapper.writeValueAsString(this.accountDto);
//
//        given(accountService.updateAccount(any(Long.class), any(AccountDto.class))).willReturn(this.accountDto);
//
//        this.mockMvc.perform(put("/v1/accounts/1")
//                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
}