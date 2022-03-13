package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/api")
public class TransferController {

    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    /**
     * GET - List of Transfers by Account ID
     * @param accountID - the accountID of the current user
     * @return all transfers for a given account
     */
    @RequestMapping(value = "/accounts/{id}/transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers(@PathVariable("id") int accountID) {
        return transferDao.listTransfers(accountID);
    }

    /**
     * GET - List of Transfers with Status of Pending FROM account (request made by other user)
     * @param id - accountID
     * @return all pending transfers FROM account
     */
    @RequestMapping(value = "/accounts/{id}/transfers/pending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(@PathVariable int id) {
        return transferDao.getPendingTransfers(id);
    }

    /**
     * GET - List of Transfers with Status of Pending TO account (request made by current user)
     * @param id - accountId
     * @return all pending transfers TO account
     */
    @RequestMapping(value = "/accounts/{id}/transfers/sent", method = RequestMethod.GET)
    public List<Transfer> getSentRequests(@PathVariable int id) {
        return transferDao.getSentRequests(id);
    }

    /**
     * GET - Transfer from transfer_id
     * @param id - transfer_id
     * @return a single transfer from a given transfer_id
     */
    @RequestMapping(value = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        return transferDao.getTransferById(id);
    }

    /**
     * POST - Create a new transfer
     * @param transfer - Transfer object
     * @return the Transfer object with the new transfer_id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@Valid @RequestBody Transfer transfer) {
        return transferDao.createTransfer(transfer);
    }

    /**
     * PUT - Update Transfer Approval
     * @param transfer - Transfer object
     * @param id - transfer_id to update
     * @return the newly updated Transfer object
     */
    @RequestMapping(value = "/transfers/{id}", method = RequestMethod.PUT)
    public Transfer updateTransferApproval(@Valid @RequestBody Transfer transfer, @PathVariable int id) {
        return transferDao.updateTransferApproval(transfer, id);
    }

}
