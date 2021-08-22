package online.switcheroos.api.v1.controller;

import lombok.RequiredArgsConstructor;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.dto.ResourceResponseDto;
import online.switcheroos.api.v1.model.Inet;
import online.switcheroos.api.v1.service.AccountService;
import online.switcheroos.core.HttpReqRespUtils;
import online.switcheroos.dto.AuthAccountResponseDto;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    private final JobScheduler jobScheduler;

    @Override
    public AuthAccountResponseDto authenticateAccount(AuthAccountDto authAccountDto, HttpServletRequest request, String userAgent) {
        AuthAccountResponseDto res = accountService.authenticateAccount(authAccountDto);
        Inet requestIp = new Inet(HttpReqRespUtils.getIpFromRequest(request));
        jobScheduler.enqueue(() ->
                accountService.logAuthAttempt(res.getAccountId(), res.isAuthenticated(), requestIp, userAgent));
        return res;
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        return accountService.findAccountDtoById(id);
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        return accountService.findAccountDtoByUsername(username);
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        return accountService.findAccountDtoByEmail(email);
    }

    @Override
    public ResourceResponseDto createAccount(@RequestBody NewAccountDto accountDto) {
        AccountDto account = accountService.createAccount(accountDto);
        return new ResourceResponseDto("/api/v1/accounts/" + account.getId());
        //TODO:drt - return path not static string path
    }

}
