package com.walid.transaction.service;

import com.walid.transaction.entity.RelativeBalance;
import com.walid.transaction.repository.TransactionRepo;

import java.time.LocalDateTime;
import java.util.Scanner;

import static com.walid.transaction.repository.TransactionRepo.DATE_TIME_FORMAT;

/**
 * @author Walid Moustaf
 */
public class BalanceEnquiryControllerImpl implements BalanceEnquiryController {

    private static final String QUIT_COMMAND = ":q";
    private final TransactionRepo transactionRepo;
    private Scanner reader;

    public BalanceEnquiryControllerImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override public void serve() {
        try (Scanner scanner = new Scanner(System.in)) {
            reader = scanner;
            while (true) {
                String accountId = prompt("AccountId");
                if (!QUIT_COMMAND.equals(accountId)) {
                    LocalDateTime from = LocalDateTime.parse(prompt("From"), DATE_TIME_FORMAT);
                    LocalDateTime to = LocalDateTime.parse(prompt("To"), DATE_TIME_FORMAT);
                    RelativeBalance relativeBalance = getRelativeBalance(accountId, from, to);
                    printRelativeBalance(relativeBalance);
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public RelativeBalance getRelativeBalance(String accountId,
                                              LocalDateTime from,
                                              LocalDateTime to) {
        return transactionRepo.getRelativeBalance(accountId, from, to);
    }

    @Override public void printRelativeBalance(RelativeBalance relativeBalance) {
        double moneyAmount = relativeBalance.getAmount().doubleValue();
        System.out
            .println("Relative balance for the period is: " + CURRENCY_FORMAT.format(moneyAmount));
        System.out.println(
            "Number of transactions included is: " + relativeBalance.getTransactionCount());
        System.out.printf("===================================%n%n");
    }

    private String prompt(String field) {
        System.out.printf("%s?%n>", field);
        return reader.nextLine().trim();
    }
}