package com.charity_hub.accounts.internal.core.models.device;

import java.util.Locale;

public record DeviceType(String value) {

    public static DeviceType create(String value) {
        String normalizedValue = value.toLowerCase(Locale.getDefault());
        return switch (normalizedValue) {
            case "android", "ios", "web" -> new DeviceType(value);
            default -> throw new IllegalArgumentException("Invalid device type");
        };
    }
}