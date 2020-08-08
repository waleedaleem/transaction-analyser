package com.walid.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Walid Moustaf
 */
public class Transaction {

    String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private LocalDateTime createAt;
    private BigDecimal amount;
    private Type transactionType;
    private String relatedTransaction;

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
