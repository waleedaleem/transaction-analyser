package com.walid.transaction.repository;

import com.walid.transaction.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    @Test public void testAdd_addTxn_then_add_reversal() {
        int initialCount = txnRepo.getCount();

        List<String> paymentRecord = Arrays
            .asList("TX99", "frmAcct99", "toAcct99", "08/08/2020 12:00:00", "99.99", "PAYMENT");
        Transaction paymentTxn = new Transaction(paymentRecord);
        List<String> reversalRecord = Arrays
            .asList("TX199", "frmAcct99", "toAcct99", "08/08/2020 13:00:00", "99.99", "REVERSAL",
                "TX99");
        Transaction reversalTxn = new Transaction(reversalRecord);

        txnRepo.add(paymentTxn);
        txnRepo.add(reversalTxn);

        assertEquals(initialCount, txnRepo.getCount());

        final long cancelledTxnCount = txnRepo.getTransactions().stream()
            .map(Transaction::getTransactionId)
            .filter(id -> Arrays.asList("TX99", "TX199").contains(id)).count();
        assertEquals(0, cancelledTxnCount);
    }

    @Test public void testRemove() {
        String txnId = "TX10001";
        assertNotNull(txnRepo.find(txnId));

        txnRepo.remove(txnId);

        assertNull(txnRepo.find(txnId));
    }

    @Test public void testFind() {
        assertNotNull(txnRepo.find("TX10001"));
        assertNull(txnRepo.find("TX_SHLDNT_BE_THERE"));
    }
}