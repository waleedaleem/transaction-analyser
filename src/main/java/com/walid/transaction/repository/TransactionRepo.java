package com.walid.transaction.repository;

import com.walid.transaction.entity.RelativeBalance;
import com.walid.transaction.entity.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Walid Moustaf
 */
public interface TransactionRepo {

    String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    void loadTransactions(InputStream transactionInputStream) throws IOException;

    void add(Transaction txn);

    boolean remove(String txnId);

    Transaction find(String txnId);

    int getCount();

    List<Transaction> getTransactions();

    RelativeBalance getRelativeBalance(String accountId, LocalDateTime from, LocalDateTime to);
}
