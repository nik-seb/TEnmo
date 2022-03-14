package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public TransferService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public Transfer getTransferById(Long transferId) {
        Transfer transfer = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "api/transfers/" + transferId, HttpMethod.GET, entity, Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfer;
    }

    public Transfer[] getTransferHistory(Long accountId) {
        Transfer[] transferList = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "/api/accounts/" + accountId + "/transfers", HttpMethod.GET, entity, Transfer[].class);
            transferList = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
    }

    public List<Transfer> getPendingTransfers(Long accountId) {
        List<Transfer> transferList = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "/api/accounts/" + accountId + "/transfers/pending", HttpMethod.GET, entity, Transfer[].class);

            if (response.getBody() != null) {
                transferList = List.of(response.getBody());
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
    }

    public List<Transfer> getSentRequests(Long accountId) {
        List<Transfer> transferList = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "/api/accounts/" + accountId + "/transfers/sent", HttpMethod.GET, entity, Transfer[].class);

            if (response.getBody() != null) {
                transferList = List.of(response.getBody());
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
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

    public boolean approveOrRejectTransfer (Transfer transfer) {
        long transfer_id = transfer.getTransferId();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "api/transfers/" + transfer_id, HttpMethod.PUT, entity, Transfer.class);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

}
