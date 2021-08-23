package online.switcheroos.api.v1.controller;

import com.github.fge.jsonpatch.JsonPatch;
import online.switcheroos.api.v1.dto.AccountDto;
import online.switcheroos.api.v1.dto.AuthAccountDto;
import online.switcheroos.api.v1.dto.NewAccountDto;
import online.switcheroos.api.v1.dto.ResourceResponseDto;
import online.switcheroos.dto.AuthAccountResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


public interface AccountController {

    @PostMapping("/authenticate")
    AuthAccountResponseDto authenticateAccount(@RequestBody AuthAccountDto authAccountDto,
                                               HttpServletRequest request,
                                               @RequestHeader(value = "User-Agent") String userAgent);

    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable UUID id);

    @GetMapping("/username/{username}")
    AccountDto getAccountByUsername(@PathVariable String username);

    @GetMapping("/email/{email}")
    AccountDto getAccountByEmail(@PathVariable String email);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResourceResponseDto createAccount(@RequestBody NewAccountDto accountDto, HttpServletRequest request);

    @PatchMapping(value = "/{username}", consumes = "application/json-patch+json")
    AccountDto updateAccount(@PathVariable String username, @RequestBody JsonPatch patch);

}
