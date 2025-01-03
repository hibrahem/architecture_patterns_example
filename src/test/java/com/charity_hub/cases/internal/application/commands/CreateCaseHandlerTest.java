package com.charity_hub.cases.internal.application.commands;

import com.charity_hub.cases.internal.applicaiton.commands.CaseResponse;
import com.charity_hub.cases.internal.applicaiton.commands.CreateCase;
import com.charity_hub.cases.internal.applicaiton.commands.CreateCaseHandler;
import com.charity_hub.cases.internal.domain.contracts.ICaseRepo;
import com.charity_hub.cases.internal.domain.models.Case;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateCaseHandlerTest {

    @Mock
    private ICaseRepo caseRepo;

    private CreateCaseHandler handler;
    private ArgumentCaptor<Case> caseCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateCaseHandler(caseRepo);
        caseCaptor = ArgumentCaptor.forClass(Case.class);
    }

    @Test
    void handle_ShouldCreateCaseAndReturnResponse() {
        // Given
        int expectedCode = 12345;
        when(caseRepo.nextCode()).thenReturn(expectedCode);

        CreateCase command = new CreateCase(
                "Test Case",
                "Test Description",
                1000,
                "OPENED",
                List.of("https://valid-doc-url.pdf", "https://valid-doc2-url.pdf"),
                List.of("tag1", "tag2")
        );

        // When
        CaseResponse response = handler.handle(command);

        // Then
        assertEquals(expectedCode, response.code());
        verify(caseRepo).nextCode();
        verify(caseRepo).save(any(Case.class));
    }

    @Test
    void handle_ShouldCreateCaseWithCorrectValues() {
        // Given
        int expectedCode = 12345;
        when(caseRepo.nextCode()).thenReturn(expectedCode);

        CreateCase command = new CreateCase(
                "Test Case",
                "Test Description",
                1000,
                "OPENED",
                List.of("https://valid-doc-url.pdf"),
                List.of("tag1")
        );

        // When
        handler.handle(command);

        // Then
        verify(caseRepo).save(caseCaptor.capture());
        Case savedCase = caseCaptor.getValue();

        assertEquals(expectedCode, savedCase.code());
        assertEquals("Test Case", savedCase.title());
        assertEquals("Test Description", savedCase.description());
    }
}
