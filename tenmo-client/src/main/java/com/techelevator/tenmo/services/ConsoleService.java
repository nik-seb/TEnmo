package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
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

    public void printAllUsers(List<Account> accountList) {
        System.out.println();
        for (int i = 0; i < accountList.size(); i += 1) {
            System.out.print(i + 1);
            System.out.println(": " + accountList.get(i).getUser().getUsername());
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
        System.out.println("---------------------------------------------------");
        System.out.printf("%28s\n", "Transfers");
        System.out.printf("%-17s %-10s %22s", "ID", "From / To", "Amount");
        System.out.println("---------------------------------------------------");
        for (Transfer transfer : transfers) {
            System.out.println(transfer.getAccountSummary(account_id));
        }
    }

    public void printUserRequests(List<Transfer> transfers) {
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.printf("%28s\n", "Sent Requests");
        System.out.printf("%-17s %-7s %25s\n", "ID", "To", "Amount");
        System.out.println("---------------------------------------------------");
        for (Transfer transfer : transfers) {
            long accountId = transfer.getAccount_to().getAccount_id();
            String username = transfer.getAccount_to().getUser().getUsername();
            BigDecimal amount = transfer.getAmount();
            System.out.printf("%-17s %-23s %2s %-8s\n", transfer.getTransfer_id(), username, "$",amount);
        }
        System.out.println();
    }

    public void printPendingTransfers(List<Transfer> transfers) {
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.printf("%28s\n", "Pending Requests");
        System.out.printf("%-17s %-10s %22s\n", "ID", "From", "Amount");
        System.out.println("---------------------------------------------------");
        for (Transfer transfer : transfers) {
            long accountId = transfer.getAccount_from().getAccount_id();
            String username = transfer.getAccount_from().getUser().getUsername();
            BigDecimal amount = transfer.getAmount();
            System.out.printf("%-17s %-23s %2s %-8s\n", transfer.getTransfer_id(), username, "$",amount);

        }
        System.out.println();
    }

    public void printPendingTransferOptions () {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("---------");
    }

    public void printTransferDetails (Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.printf("%28s\n", "Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println(transfer.toString());
    }

}
