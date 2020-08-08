package com.walid.transaction.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Walid Moustafa
 */
public class RelativeBalance {

    private AtomicReference<BigDecimal> amount = new AtomicReference<>(BigDecimal.ZERO);
    private AtomicLong transactionCount = new AtomicLong(0);

    public void addAmount(BigDecimal amount) {
        boolean added = false;
        while (!added) {
            BigDecimal currentAmount = this.amount.get();
            added = this.amount.compareAndSet(currentAmount, currentAmount.add(amount));
        }
        this.transactionCount.incrementAndGet();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RelativeBalance that = (RelativeBalance) o;
        return amount.equals(that.amount) && transactionCount.equals(that.transactionCount);
    }

    @Override public int hashCode() {
        return Objects.hash(amount, transactionCount);
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public long getTransactionCount() {
        return transactionCount.get();
    }

    @Override public String toString() {
        return "RelativeBalance{" + "amount=" + amount + ", transactionCount=" + transactionCount
            + '}';
    }
}
