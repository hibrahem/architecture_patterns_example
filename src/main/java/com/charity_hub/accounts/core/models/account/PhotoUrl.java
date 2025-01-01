package com.charity_hub.accounts.core.models.account;

import com.charity_hub.accounts.core.validators.ValueValidator;

import java.util.regex.Pattern;

public record PhotoUrl(String value) {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)"
    );

    public PhotoUrl {
        ValueValidator.assertNotEmpty(value, getClass());
        ValueValidator.assertValidFormat(value, URL_PATTERN, getClass());
    }

    public static PhotoUrl create(String value) {
        return new PhotoUrl(value);
    }

    public String value() {
        return value;
    }
}