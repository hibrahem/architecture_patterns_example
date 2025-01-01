package com.charity_hub.accounts.internal.core.models.account;

import com.charity_hub.accounts.internal.core.validators.ValueValidator;

import java.util.regex.Pattern;

public record MobileNumber(String value) {
    private static final int MIN_LENGTH = 7;
    private static final int MAX_LENGTH = 15;
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]*$");

    public MobileNumber {
        ValueValidator.assertWithinRange(getClass(), value, MIN_LENGTH, MAX_LENGTH);
        ValueValidator.assertValidFormat(value, MOBILE_PATTERN, getClass());
    }

    public static MobileNumber of(String value) {
        String modifiedNumber = value;
        if (modifiedNumber.startsWith("00")) {
            modifiedNumber = modifiedNumber.replace("00", "");
        }
        if (modifiedNumber.startsWith("+")) {
            modifiedNumber = modifiedNumber.replace("+", "");
        }
        return new MobileNumber(modifiedNumber);
    }
}