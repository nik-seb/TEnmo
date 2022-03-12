package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

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
        System.out.println();
        System.out.println("            ████████ ███████ ███    ██ ███    ███  ██████            ");
        System.out.println("               ██    ██      ████   ██ ████  ████ ██    ██           ");
        System.out.println("  █████        ██    █████   ██ ██  ██ ██ ████ ██ ██    ██     █████ ");
        System.out.println("               ██    ██      ██  ██ ██ ██  ██  ██ ██    ██           ");
        System.out.println("               ██    ███████ ██   ████ ██      ██  ██████            ");
        System.out.println();
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
        String[] headings = new String[2];
        headings[0] = String.format("%28s", "Transfers");
        headings[1] = String.format("%-17s %-10s %15s", "ID", "From / To", "Amount");
        printHeading(headings);

        for (Transfer transfer : transfers) {
            System.out.println(transfer.getAccountSummary(account_id));
        }

        System.out.println("--------------------------------------------");
        System.out.println();
    }

    public void printUserRequests(List<Transfer> transfers) {
        String[] headings = new String[2];
        headings[0] = String.format("%28s", "Sent Requests");
        headings[1] = String.format("%-17s %-7s %18s", "ID", "To", "Amount");
        printHeading(headings);

        for (Transfer transfer : transfers) {

            System.out.println(transfer.getPendingSumamry(false));
        }

        System.out.println("--------------------------------------------");
        System.out.println();
    }

    public void printPendingTransfers(List<Transfer> transfers) {
        String[] headings = new String[2];
        headings[0] = String.format("%28s", "Pending Requests");
        headings[1] = String.format("%-17s %-10s %14s", "ID", "From", "Amount");
        printHeading(headings);

        for (Transfer transfer : transfers) {
            System.out.println(transfer.getPendingSumamry(true));
        }

        System.out.println("--------------------------------------------");
        System.out.println();
    }



    public void printTransferDetails (Transfer transfer) {
        String[] headings = {String.format("%" + 28 + "s", "Transfer Details")};
        printHeading(headings);

        System.out.println(transfer.toString());
        System.out.println("--------------------------------------------");
    }

    public void printHeading (String[] heading) {
        System.out.println();
        System.out.println("--------------------------------------------");
        for (String string : heading) {
            System.out.println(string);
        }
        System.out.println("--------------------------------------------");
    }

}
