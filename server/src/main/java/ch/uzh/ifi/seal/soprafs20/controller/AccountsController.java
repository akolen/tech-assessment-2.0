package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Account;
import ch.uzh.ifi.seal.soprafs20.rest.dto.AccountGetDTO;
import ch.uzh.ifi.seal.soprafs20.service.AccountService;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.json.*;

@RestController
public class AccountsController {

    private final AccountService accountService;

    AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Account> getAllAccounts() throws IOException {

        String accessToken = accountService.getAccessToken();
        String consentId = accountService.postAccountRequest(accessToken);
        String authorizationCode = accountService.getConsentApproval(consentId);
        String APIaccessToken = accountService.exchangeCodeForAccessToken(authorizationCode);
        List<Account> accountsList = accountService.getAccounts(APIaccessToken);

        System.out.println(accountsList);

        return accountsList;
    }


}
