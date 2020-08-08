package com.walid.transaction.repository;

import com.walid.transaction.entity.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Walid Moustaf
 */
public class TransactionRepoImpl implements TransactionRepo {

    private static List<Transaction> transactions = new LinkedList<>();

    @Override public void loadTransactions(String transactionsFilePath) throws IOException {
        Reader in = new FileReader(transactionsFilePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        for (CSVRecord record : records) {
            add(new Transaction(record));
        }
    }

    @Override public void add(Transaction txn) {
        if (Transaction.Type.PAYMENT == txn.getTransactionType()) {
            transactions.add(txn);
        } else {
            // REVERSAL, then remove associated payment
            remove(txn.getRelatedTransaction());
        }
    }

    @Override public boolean remove(String txnId) {
        return transactions.remove(find(txnId));
    }

    @Override public Transaction find(String txnId) {
        if (txnId != null) {
            try {
                return transactions.parallelStream()
                    .filter(txn -> txnId.equals(txn.getTransactionId())).findAny().get();
            } catch (NoSuchElementException e) {
                return null;
            }
        }
        return null;
    }
}
