package com.charity_hub.accounts.internal.core.models.account;

import com.charity_hub.accounts.internal.core.validators.ValueValidator;

public record FullName(String value) {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    public FullName {
        ValueValidator.assertWithinRange(getClass(), value, MIN_LENGTH, MAX_LENGTH);
    }

    public static FullName create(String value) {
        return new FullName(value);
    }

}