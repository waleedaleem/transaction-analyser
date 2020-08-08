package com.walid.transaction.service;

import com.walid.transaction.entity.RelativeBalance;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * @author Walid Moustaf
 */
public interface BalanceEnquiryController {

    NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.getDefault());

    void serve();

    RelativeBalance getRelativeBalance(String accountId, LocalDateTime from, LocalDateTime to);

    void printRelativeBalance(RelativeBalance relativeBalance);
}
