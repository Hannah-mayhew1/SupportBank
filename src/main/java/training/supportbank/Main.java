package training.supportbank;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String args[]) throws FileNotFoundException, IOException {
// imports data
        BufferedReader Transactions2014 = new BufferedReader(
                new InputStreamReader(new FileInputStream("/Users/hannah.mayhew/Documents/Work/Training/SupportBank/Transactions2014.csv")));

        List<Transaction> transactions = new ArrayList<>();
// increments through the list transactions, splits the list @ commas, creates a new list 'pieces', assigns data to class transaction, adds transaction to transaction list
        try {
            String line;
            while ((line = Transactions2014.readLine()) != null) {
                Transaction transaction = new Transaction();
                List <String> pieces = Arrays.asList (line.split(","));
                    transaction.date = LocalDate.parse (pieces.get(0), (DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    transaction.from = pieces.get(1);
                    transaction.to = pieces.get(2);
                    transaction.narrative = pieces.get(3);
                    transaction.amount = Double.parseDouble (pieces.get(4));

                    transactions.add(transaction);
            }
            System.out.println(transactions);
// creates a new list 'accounts', goes through transactions and declares to/from names, calls in the getAccountForPerson method
// creates a new account (consisting of name, transactions, balance) if null
            List<Account> accounts = new ArrayList<>();
            for (int i = 0; i < transactions.size(); i++) {
                String fromName = transactions.get(i).from;
                String toName = transactions.get(i).to;

                if (getAccountForPerson(accounts, fromName)==null) {
                    Account personal = new Account();
                    personal.name = fromName;
                    personal.transactions = new ArrayList<>();
                    personal.balance = 0.0;
                    accounts.add(personal);
                }

                if (getAccountForPerson(accounts, toName)==null) {
                    Account personal = new Account();
                    personal.name = toName;
                    personal.transactions = new ArrayList<>();
                    personal.balance = 0.0;
                    accounts.add(personal);
                }
//adds transactions to account, alters balance based on transactions
                Account fromAccount = getAccountForPerson(accounts, fromName);
                fromAccount.transactions.add(transactions.get(i));
                fromAccount.balance = fromAccount.balance - transactions.get(i).amount;

                Account toAccount = getAccountForPerson(accounts, toName);
                toAccount.transactions.add(transactions.get(i));
                toAccount.balance = toAccount.balance + transactions.get(i).amount;
            }
//user input
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.equals("List All")) {
                for (int i = 0; i < accounts.size(); i++) {
                    System.out.println(accounts.get(i).name);
                    System.out.println(accounts.get(i).balance);
                }
            }
        } finally {
            Transactions2014.close();
        }
    }

    private static Account getAccountForPerson(List<Account> accounts, String name) {
        for (int i = 0; i < accounts.size(); i++) {
            Account thisAccount = accounts.get(i);
            if (thisAccount.name.equals(name)) {
                return thisAccount;
            }
        }
        return null;
    }
}
