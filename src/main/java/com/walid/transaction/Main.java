package com.walid.transaction;

import com.walid.transaction.repository.TransactionRepo;
import com.walid.transaction.repository.TransactionRepoImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Walid Moustaf
 */
public class Main {

    public static final TransactionRepo REPO = new TransactionRepoImpl();
    private static final String[] HELP_ALIASES = { "-h", "-help", "--help" };

    public static void main(String[] args) throws IOException {
        if (args.length != 1 || Arrays.asList(HELP_ALIASES).contains(args[0])) {
            System.out
                .println("usage: java -jar transaction-analyser <startup transactions CSV file>");
            System.exit(0);
        }
        try (FileInputStream txnInputStream = new FileInputStream(args[0])) {
            REPO.loadTransactions(txnInputStream);
        }
    }
}
