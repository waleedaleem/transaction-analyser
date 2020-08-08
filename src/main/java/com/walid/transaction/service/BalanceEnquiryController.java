package com.walid.transaction.service;

import com.walid.transaction.entity.RelativeBalance;

import java.time.LocalDateTime;

/**
 * @author Walid Moustaf
 */
public interface BalanceEnquiryController {

    void serve();

    RelativeBalance getRelativeBalance(String accountId, LocalDateTime from, LocalDateTime to);

    void printRelativeBalance(RelativeBalance relativeBalance);
}
