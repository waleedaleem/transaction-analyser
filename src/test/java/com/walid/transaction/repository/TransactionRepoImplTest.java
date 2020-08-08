package com.walid.transaction.repository;

import com.walid.transaction.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Walid Moustaf
 */
public class TransactionRepoImplTest {

    private static final String TRANSACTION_SAMPLE_FILE = "startupTransactions.csv";

    private TransactionRepo txnRepo;

    @Before public void LoadTransactions() throws IOException {
        txnRepo = new TransactionRepoImpl();
        InputStream txnStream = this.getClass().getClassLoader()
            .getResourceAsStream(TRANSACTION_SAMPLE_FILE);
        txnRepo.loadTransactions(txnStream);
    }

    @Test public void testLoadTransactions() {
        // sample file includes 5 txns (two of them cancel each other)
        assertEquals(3, txnRepo.getCount());
    }

    @Test public void testLoadTransactions_reverse_txns_not_persisted() {
        final long reversalCount = txnRepo.getTransactions().parallelStream()
            .filter(txn -> txn.getTransactionType() == Transaction.Type.REVERSAL).count();
        assertEquals(0, reversalCount);
    }

    @Test public void testAdd() {
    }

    @Test public void testRemove() {
    }

    @Test public void testFind() {
    }
}