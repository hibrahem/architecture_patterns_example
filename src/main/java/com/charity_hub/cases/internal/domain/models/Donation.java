package com.charity_hub.cases.internal.domain.models;

import java.util.UUID;

public class Donation {
    private int amount;
    private int caseCode;
    private UUID customerId;

    public Donation(int amount, int caseCode, UUID customerId) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Donation amount must be greater than zero");
        }
        if (caseCode <= 0) {
            throw new IllegalArgumentException("Case code must be a positive number");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        
        this.amount = amount;
        this.caseCode = caseCode;
        this.customerId = customerId;
    }
}
