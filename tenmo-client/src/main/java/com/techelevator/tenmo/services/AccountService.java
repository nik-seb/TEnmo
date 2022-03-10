package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + userId + "/balance", HttpMethod.GET, entity, BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public Account getAccountById(Long userId) {
        Account account = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + userId, HttpMethod.GET, entity, Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

    public void getTransferHistory() {
        // TODO add this method
    }

    public void getPendingRequests() {
        // TODO add this method
    }

    public Transfer createNewTransfer(Transfer newTransfer) {
        Transfer transfer = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "api/transfers", HttpMethod.POST, entity, Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;
    }

    public void updateAccount(Account accountToUpdate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> entity = new HttpEntity<>(accountToUpdate, headers);

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "api/accounts/" + accountToUpdate.getUser_id(), HttpMethod.PUT, entity, Account.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Transfer sendBucks(Transfer newTransfer) {
        Transfer transfer = createNewTransfer(newTransfer);

        Account newAccountFrom = null;
        Account newAccountTo = null;

        // handle the accounts after the transfer was created
        if (transfer != null) {
            Account accountFrom = transfer.getAccount_from();
            Account accountTo = transfer.getAccount_to();

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

    public void requestBucks() {
        // TODO add this method
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
}
