package com.charity_hub.cases.internal.domain.models;

import com.charity_hub.cases.internal.domain.models.fixtures.CaseFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {

    @Test
    void newCase_ShouldCreateCaseWithCorrectBasicValues() {
        // When
        Case case_ = CaseFixture.createDefault();

        // Then
        assertEquals(CaseFixture.CODE, case_.code());
        assertEquals(CaseFixture.TITLE, case_.title());
        assertEquals(CaseFixture.DESCRIPTION, case_.description());
    }

    @Test
    void newCase_WhenStatusIsOpened_ShouldAddCaseOpenedEvent() {
        // When
        Case case_ = CaseFixture.createDefault();
        List<CaseEvent> events = case_.getOccurredEvents();

        // Then
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof CaseOpened);
        CaseOpened openedEvent = (CaseOpened) events.get(0);
        assertEquals(case_.code(), openedEvent.caseCode());
    }

    @Test
    void newCase_WhenStatusIsNotOpened_ShouldNotAddCaseOpenedEvent() {
        // When
        Case case_ = CaseFixture.createWithStatus(CaseFixture.STATUS_CLOSED);
        List<CaseEvent> events = case_.getOccurredEvents();

        // Then
        assertTrue(events.isEmpty());
    }

    @Test
    void getOccurredEvents_ShouldClearEventsAfterRetrieving() {
        // Given
        Case case_ = CaseFixture.createDefault();

        // When
        List<CaseEvent> firstRetrieval = case_.getOccurredEvents();
        List<CaseEvent> secondRetrieval = case_.getOccurredEvents();

        // Then
        assertFalse(firstRetrieval.isEmpty());
        assertTrue(secondRetrieval.isEmpty());
    }

    @Test
    void getOccurredEvents_ShouldReturnDefensiveCopy() {
        // Given
        Case case_ = CaseFixture.createDefault();

        // When
        List<CaseEvent> events = case_.getOccurredEvents();
        events.clear(); // Try to modify the returned list
        List<CaseEvent> newEvents = case_.getOccurredEvents();

        // Then
        assertTrue(newEvents.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"OPENED", "CLOSED", "DRAFT"})
    void newCase_ShouldAcceptValidStatuses(String status) {
        // When/Then
        assertDoesNotThrow(() -> CaseFixture.createWithStatus(status));
    }

    @Test
    void newCase_ShouldThrowExceptionForInvalidStatus() {
        // When/Then
        assertThrows(IllegalArgumentException.class,
                () -> CaseFixture.createWithStatus("INVALID_STATUS"));
    }
} 
