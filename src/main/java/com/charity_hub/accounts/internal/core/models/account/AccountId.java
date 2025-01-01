package com.charity_hub.accounts.internal.core.models.account;

import java.util.UUID;

public record AccountId(UUID value) {

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }
}