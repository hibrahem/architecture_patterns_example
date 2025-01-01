package com.charity_hub.accounts.internal.core.models.account;

public enum Permission {
    FULL_ACCESS,
    VIEW,
    CREATE_CASES;

    /**
     * Creates a Permission from its string representation.
     *
     * @param permission the string representation of the permission
     * @return the corresponding Permission enum value
     * @throws IllegalArgumentException if the string doesn't match any Permission value
     */
    public static Permission fromString(String permission) {
        return valueOf(permission);
    }

    public static Permission of(boolean isAdmin) {
        return isAdmin ? FULL_ACCESS : VIEW;
    }
}