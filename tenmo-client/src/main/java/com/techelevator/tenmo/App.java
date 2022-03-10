package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);

    private AuthenticatedUser currentUser;

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
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		BigDecimal balance = accountService.getCurrentBalance(currentUser.getUser().getId());
        consoleService.printBalance(balance);
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        List<User> userList = userService.getAllUsers();

        int maxSelection = userList.size();
        int userSelection = -1;
        while (userSelection != 0) {
            consoleService.printAllUsers(userList);

            userSelection = consoleService.promptForMenuSelection("Please select a user... ");

            if (userSelection > 0 && userSelection <= maxSelection) {
                User user = userList.get(userSelection - 1);
                if (user.equals(currentUser.getUser())) {
                    System.out.println("You can't send money to yourself!");
                    continue;
                }

                BigDecimal currentBalance = accountService.getCurrentBalance(currentUser.getUser().getId());

                BigDecimal amountToTransfer = BigDecimal.ZERO;
                while (amountToTransfer.compareTo(BigDecimal.ONE) < 0) {
                    amountToTransfer = consoleService.promptForBigDecimal("Please enter an amount to transfer");

                    if (amountToTransfer.compareTo(currentBalance) >= 0) {
                        System.out.println("Insignificant funds!");
                        amountToTransfer = BigDecimal.ZERO;
                    }
                }

                Transfer newTransfer = new Transfer();

                newTransfer.setAccount_from(currentUser.getUser().getId());
                newTransfer.setAccount_to(user.getId());
                newTransfer.setTransfer_status(TransferStatus.APPROVED);
                newTransfer.setTransfer_type(TransferType.SEND);
                newTransfer.setTransferAmount(amountToTransfer);


                Transfer transfer = accountService.sendBucks(newTransfer);

            } else if (userSelection != 0) {
                System.out.println("Invalid selection");
            }

        }

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
