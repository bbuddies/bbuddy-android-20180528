package com.odde.bbuddy.account.builder;

import com.odde.bbuddy.account.viewmodel.Account;

public class AccountBuilder {

    public static Account.AccountBuilder anAccount() {
        return Account.builder().id(1).name("name").balanceBroughtForward(100);
    }

    public static Account.AccountBuilder emptyAccount() {
        return Account.builder();
    }
}
