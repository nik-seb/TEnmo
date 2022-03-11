package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printAllUsers(List<User> userList) {
        System.out.println();
        for (int i = 0; i < userList.size(); i += 1) {
            System.out.print(i + 1);
            System.out.println(": " + userList.get(i).getUsername());
        }
        System.out.println("0: Exit");
        System.out.println("");
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printBalance(BigDecimal balance) {
        System.out.println("Your current balance is: " + balance);
    }

    public void printTransferHistory (Transfer[] transfers, Long account_id) {
        System.out.println("------------------------------------------- \n" + "Transfers \n" +
                "ID          From/To                 Amount \n" +
                "-------------------------------------------");
        for (Transfer transfer : transfers) {
            String toOrFrom = transfer.getAccount_from().getAccount_id() == account_id ? "From: " : "To:    ";
            String output = transfer.getTransfer_id() + "          " + toOrFrom + account_id + "          " +
                    "$ " + transfer.getAmount();
            System.out.println(output);
        }
        System.out.print("Please enter transfer ID to view details (0 to cancel): ");
    }

    public void printTransferDetails (Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println("Id: " + transfer.getTransfer_id());
        System.out.println("From: "); // TODO here and below, get actual user names
        System.out.println("To: ");
        System.out.println("Type: " + transfer.getTransfer_type_id()); // TODO here and below, get description instead of id
        System.out.println("Status: " + transfer.getTransfer_status_id());
        System.out.println("Amount: $" + transfer.getAmount());
    }

}
