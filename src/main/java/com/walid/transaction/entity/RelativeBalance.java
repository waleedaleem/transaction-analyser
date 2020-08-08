package com.walid.transaction.entity;

import java.math.BigDecimal;

/**
 * @author Walid Moustafa
 */
public class RelativeBalance {

    private BigDecimal amount = BigDecimal.ZERO;
    private long transactionCount = 0;

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
        this.transactionCount++;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getTransactionCount() {
        return transactionCount;
    }
}
