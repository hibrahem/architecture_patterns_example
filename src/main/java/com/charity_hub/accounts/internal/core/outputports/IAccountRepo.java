package com.charity_hub.accounts.internal.core.outputports;

import com.charity_hub.accounts.internal.core.models.account.Account;

import java.util.concurrent.CompletableFuture;

public interface IAccountRepo {
    CompletableFuture<Account> getByMobileNumber(String mobileNumber);

    CompletableFuture<Void> save(Account account);

    CompletableFuture<Boolean> isAdmin(String mobileNumber);
}