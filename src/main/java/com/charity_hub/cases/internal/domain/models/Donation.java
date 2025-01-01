package com.charity_hub.cases.internal.domain.models;

import java.util.UUID;

public class Donation {
    private int amount;
    private int caseCode;
    private UUID customerId;

    public Donation(int amount, int caseCode, UUID customerId) {
        this.amount = amount;
        this.caseCode = caseCode;
        this.customerId = customerId;
    }
}
