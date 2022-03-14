package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getCurrentBalance(Long userId) {
        BigDecimal balance = null;

        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + userId + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public Account getAccountById(Long userId) {
        Account account = null;

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + userId, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        try {
            ResponseEntity<Account[]> response =
                    restTemplate.exchange(baseUrl + "api/accounts", HttpMethod.GET, makeAuthEntity(), Account[].class);
            if (response.getBody() != null) {
                accounts = List.of(response.getBody());
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return accounts;
    }

    public void updateAccount(Account accountToUpdate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity<>(accountToUpdate, headers);

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + accountToUpdate.getUser(), HttpMethod.PUT, entity, Account.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Transfer sendBucks(Transfer transfer) {
        Account newAccountFrom = null;
        Account newAccountTo = null;

        // handle the accounts after the transfer was created
        if (transfer != null) {
            Account accountFrom = transfer.getAccountFrom();
            Account accountTo = transfer.getAccountTo();

            newAccountFrom = sendMoney(accountFrom, transfer.getAmount());
            newAccountTo = receiveMoney(accountTo, transfer.getAmount());
        }

        if (newAccountFrom != null) {
            updateAccount(newAccountFrom);
        }

        if (newAccountTo != null) {
            updateAccount(newAccountTo);
        }

        return transfer;
    }

    private Account sendMoney(Account account, BigDecimal amountToSend) {
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amountToSend);

        account.setBalance(newBalance);

        return account;
    }

    private Account receiveMoney(Account account, BigDecimal amountToReceive) {
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amountToReceive);

        account.setBalance(newBalance);

        return account;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
}
