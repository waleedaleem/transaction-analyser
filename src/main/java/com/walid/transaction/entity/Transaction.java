package com.walid.transaction.entity;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.walid.transaction.repository.TransactionRepo.DATE_TIME_FORMAT;

/**
 * @author Walid Moustaf
 */
public class Transaction {

    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime createAt;
    private BigDecimal amount;
    private Type transactionType;
    private String relatedTransaction;

    public Transaction(CSVRecord record) {
        int index = 0;
        transactionId = record.get(index++);
        fromAccountId = record.get(index++);
        toAccountId = record.get(index++);
        createAt = LocalDateTime.parse(record.get(index++), DATE_TIME_FORMAT);
        amount = new BigDecimal(record.get(index++));
        transactionType = Type.valueOf(record.get(index++));
        if (index < record.size()) {
            relatedTransaction = record.get(index++);
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public String getRelatedTransaction() {
        return relatedTransaction;
    }

    @Override public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Transaction that = (Transaction) other;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override public String toString() {
        return "Transaction{" + "transactionId='" + transactionId + '\'' + ", fromAccountId='"
            + fromAccountId + '\'' + ", toAccountId='" + toAccountId + '\'' + ", createAt="
            + createAt + ", amount=" + amount + ", transactionType=" + transactionType
            + ", relatedTransaction='" + relatedTransaction + '\'' + '}';
    }

    public enum Type {
        PAYMENT, REVERSAL
    }
}
