package com.charity_hub.cases.internal.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DonationTest {

    @Test
    void should_Throw_Exception_When_Amount_Is_Zero() {
        // Given
        int amount = 0;
        int caseCode = 12345;
        UUID customerId = UUID.randomUUID();

        // When/Then
        assertThrows(IllegalArgumentException.class, () ->
                        new Donation(amount, caseCode, customerId),
                "Donation amount must be greater than zero"
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -1000})
    void should_Throw_Exception_When_Amount_Is_Negative(int negativeAmount) {
        // Given
        int caseCode = 12345;
        UUID customerId = UUID.randomUUID();

        // When/Then
        assertThrows(IllegalArgumentException.class, () ->
                        new Donation(negativeAmount, caseCode, customerId),
                "Donation amount cannot be negative"
        );
    }

    @Test
    void should_Throw_Exception_When_Customer_Id_Is_Null() {
        // Given
        int amount = 100;
        int caseCode = 12345;
        UUID customerId = null;

        // When/Then
        assertThrows(IllegalArgumentException.class, () ->
                        new Donation(amount, caseCode, customerId),
                "Customer ID cannot be null"
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    void should_Throw_Exception_When_Case_Code_Is_Invalid(int invalidCaseCode) {
        // Given
        int amount = 100;
        UUID customerId = UUID.randomUUID();

        // When/Then
        assertThrows(IllegalArgumentException.class, () ->
                        new Donation(amount, invalidCaseCode, customerId),
                "Case code must be a positive number"
        );
    }
} 
