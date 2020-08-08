package com.walid.transaction.repository;

import com.walid.transaction.entity.Transaction;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author Walid Moustaf
 */
public interface TransactionRepo {

    String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    void loadTransactions(String transactionsFilePath) throws IOException;

    void add(Transaction txn);

    boolean remove(String txnId);

    Transaction find(String txnId);
}
