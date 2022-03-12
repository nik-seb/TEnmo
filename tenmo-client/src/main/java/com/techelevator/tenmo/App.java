package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private Account userAccount;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setAuthToken(currentUser.getToken());
            userService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
            userAccount = accountService.getAccountById(currentUser.getUser().getId());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendOrRequestBucks(TransferType.SEND);
            } else if (menuSelection == 5) {
                sendOrRequestBucks(TransferType.REQUEST);
            } else if (menuSelection == 0) {
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid Selection");
            }
        }
    }

	private void viewCurrentBalance() {
		BigDecimal balance = accountService.getCurrentBalance(currentUser.getUser().getId());
        consoleService.printBalance(balance);
	}

	private void viewTransferHistory() {
        Transfer[] transferHistory = transferService.getTransferHistory(userAccount.getAccount_id(), TransferStatus.APPROVED);
        if (transferHistory != null){
            consoleService.printTransferHistory(transferHistory, userAccount.getAccount_id());
        } else {
            System.out.println("No transfers were found for this user.");
        }
        int selection = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
        if (selection != 0) {
            Transfer selectedTransfer = transferService.getTransferById((long) selection);
            consoleService.printTransferDetails(selectedTransfer);
        }
	}

	private void viewPendingRequests() {
        List<Transfer> userRequests = transferService.getSentRequests(userAccount.getAccount_id());
        if (!userRequests.isEmpty()) {
            consoleService.printUserRequests(userRequests);
        }

        List<Transfer> pendingTransfers = transferService.getPendingTransfers(userAccount.getAccount_id());
        if (!pendingTransfers.isEmpty()) {
            consoleService.printPendingTransfers(pendingTransfers);

            boolean isValidSelection = false;
            int selectedTransferId = 0;
            Transfer transferToUpdate = null;

            while (!isValidSelection) {
                selectedTransferId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");

                if (selectedTransferId == 0) {
                    return;
                }

                for (Transfer transfer : pendingTransfers) {
                    if (transfer.getTransfer_id() == selectedTransferId) {
                        transferToUpdate = transfer;
                        isValidSelection = true;
                        break;
                    }
                }
            }

            consoleService.printPendingTransferOptions();
            int selectedAction = consoleService.promptForMenuSelection("Please choose an option: ");
            boolean isUpdated = false;
            switch (selectedAction) {
                case 1:
                    if (userAccount.getBalance().compareTo(transferToUpdate.getAmount()) >= 0) {
                        isUpdated = transferService.approveOrRejectTransfer(selectedTransferId, true);
                        if (isUpdated) {
                            Transfer returnedTransfer = accountService.sendBucks(transferToUpdate);
                            if (returnedTransfer != null) {
                                System.out.println("You have successfully sent the transfer.");
                                System.out.println("Your new balance is: " + returnedTransfer.getAccount_from().getBalance());
                            } else {
                                System.out.println("There has been a problem updating your balance.");
                            }
                        } else {
                            System.out.println("Sorry, there's been a problem. The transfer was unsuccessful.");
                        }
                    } else {
                        System.out.println("You don't have sufficient balance to send that transfer.");
                    }
                    break;
                case 2: isUpdated = transferService.approveOrRejectTransfer(selectedTransferId, false);
                    break;
                default:
                    System.out.println("This transfer has not been approved or rejected.");
            }
            if (!isUpdated) {
                System.out.println("No update has been made to this transfer.");
            }
        }

        if (userRequests.isEmpty() && pendingTransfers.isEmpty()) {
            System.out.println("No pending transfers found!");
        }
	}

    private void sendOrRequestBucks(TransferType transferType) {
        Account toAccount = selectAnAccountForTransfer();

        if (toAccount == null) {
            System.out.println("Exiting...");
            return;
        }
        String prompt = transferType.equals(TransferType.SEND) ? "transfer: " : "request: ";

        BigDecimal amountToTransfer = getAmountFromUser(transferType, "Please enter an amount to " + prompt);

        if (amountToTransfer.equals(BigDecimal.ZERO)) {
            System.out.println("Cancelling transaction...");
            return;
        }

        Transfer newTransfer = new Transfer();

        newTransfer.setAccount_from(userAccount);
        newTransfer.setAccount_to(toAccount);
        newTransfer.setTransfer_status_id(transferType.equals(TransferType.SEND)
                ? TransferStatus.APPROVED : TransferStatus.PENDING);
        newTransfer.setTransfer_type_id(transferType);
        newTransfer.setAmount(amountToTransfer);

        Transfer transfer = transferService.createNewTransfer(newTransfer);

        if (transfer != null) {
            if (transferType.equals(TransferType.REQUEST)) {
                System.out.println("Successfully requested $" + transfer.getAmount()
                        + " from: " + transfer.getAccount_to().getUser().getUsername());
            } else {
                Transfer returnedTransfer = accountService.sendBucks(transfer);

                BigDecimal newBalance = returnedTransfer.getAccount_from().getBalance();

                System.out.println("Your new balance is: " + newBalance);
            }
        } else {
            System.out.println("There was an issue sending a transfer...Please try later");
        }
    }

    private Account selectAnAccountForTransfer() {
        List<Account> accountList = accountService.getAllAccounts();

        int maxSelection = accountList.size();
        int accountSelection = -1;
        while (accountSelection != 0) {
            consoleService.printAllUsers(accountList);

            accountSelection = consoleService.promptForMenuSelection("Please select a user: ");

            if (accountSelection > 0 && accountSelection <= maxSelection) {
                Account account = accountList.get(accountSelection - 1);
                if (account.getUser().equals(currentUser.getUser())) {
                    System.out.println("You can't send money to yourself!");
                } else {
                    return account;
                }
            } else {
                System.out.println("Invalid selection!");
            }
        }

        return null;
    }

    private BigDecimal getAmountFromUser(TransferType transferType, String prompt) {
        BigDecimal currentBalance = userAccount.getBalance();

        BigDecimal amountToTransfer = BigDecimal.valueOf(-1);
        while (amountToTransfer.compareTo(BigDecimal.ONE) <= 0) {
            amountToTransfer = consoleService.promptForBigDecimal(prompt);

            if (amountToTransfer.compareTo(BigDecimal.valueOf(-1)) <= 0) {
                System.out.println("Not a valid number!");
                continue;
            }

            if (amountToTransfer.compareTo(currentBalance) >= 0 && transferType.equals(TransferType.SEND)) {
                System.out.println("Insignificant funds!");
                amountToTransfer = BigDecimal.valueOf(-1);
                continue;
            }

            return amountToTransfer;
        }

        return amountToTransfer;
    }

}
