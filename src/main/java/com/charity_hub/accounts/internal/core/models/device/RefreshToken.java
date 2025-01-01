package com.charity_hub.accounts.internal.core.models.device;

import com.charity_hub.accounts.internal.core.validators.ValueValidator;

import java.util.Objects;

public class RefreshToken {
    private final String value;

    private RefreshToken(String value) {
        ValueValidator.assertNotEmpty(value, getClass());
        this.value = value;
    }

    // Static factory method (replacing companion object)
    public static RefreshToken create(String value) {
        return new RefreshToken(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "value='" + value + '\'' +
                '}';
    }
}