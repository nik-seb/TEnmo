package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.baseUrl = url;
    }

    private void getCurrentBalance() {
        // TODO add this method
    }

    private void getTransferHistory() {
        // TODO add this method
    }

    private void getPendingRequests() {
        // TODO add this method
    }

    private void sendBucks() {
        // TODO add this method
    }

    private void requestBucks() {
        // TODO add this method
    }
}
