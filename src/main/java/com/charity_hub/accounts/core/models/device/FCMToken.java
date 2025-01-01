package com.charity_hub.accounts.core.models.device;

import com.charity_hub.accounts.core.validators.ValueValidator;

import java.util.Objects;

public class FCMToken {
    private final String value;

    private FCMToken(String value) {
        ValueValidator.assertNotEmpty(value, getClass());
        this.value = value;
    }

    // Static factory method (replacing companion object)
    public static FCMToken create(String value) {
        return new FCMToken(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FCMToken fcmToken = (FCMToken) o;
        return Objects.equals(value, fcmToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FCMToken{" +
                "value='" + value + '\'' +
                '}';
    }
}