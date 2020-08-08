package com.walid.transaction.repository;

import com.walid.transaction.entity.RelativeBalance;
import com.walid.transaction.entity.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * @author Walid Moustaf
 */
public class TransactionRepoImpl implements TransactionRepo {

    private List<Transaction> transactions = new LinkedList<>();

    @Override public void loadTransactions(InputStream transactionInputStream) throws IOException {
        try (Reader in = new InputStreamReader(transactionInputStream)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                add(new Transaction(record));
            }
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

    @Override public int getCount() {
        return transactions.size();
    }

    @Override public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public RelativeBalance getRelativeBalance(String accountId,
                                              LocalDateTime from,
                                              LocalDateTime to) {
        RelativeBalance relativeBalance = new RelativeBalance();
        Predicate<Transaction> relativeTxn = txn -> txn.withinInterval(from, to);
        relativeTxn = relativeTxn.and(txn -> txn.touchesAccount(accountId));
        transactions.parallelStream().filter(relativeTxn)
            .map(txn -> txn.toAccountBalance(accountId)).forEach(relativeBalance::addAmount);
        return relativeBalance;
    }
}
