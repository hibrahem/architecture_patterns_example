package com.charity_hub.cases.internal.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Collections;

class DocumentsTest {

    @Test
    @DisplayName("Should create Documents object when given valid URLs")
    void shouldCreateDocumentsWithValidUrls() {
        // Given
        List<String> validUrls = List.of(
                "https://example.com",
                "http://subdomain.example.com/path",
                "https://example.com/path?param=value",
                "http://example.com/path#fragment"
        );

        // When
        Documents documents = Documents.of(validUrls);

        // Then
        assertNotNull(documents);
        assertEquals(validUrls, documents.documents());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when given invalid URL")
    void shouldThrowExceptionForInvalidUrls() {
        // Given
        List<String> invalidUrls = List.of(
                "not-a-url",
                "ftp://invalid-protocol.com",
                "http:/invalid-format.com"
        );

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                Documents.of(invalidUrls)
        );
        assertEquals("Invalid URL format", thrown.getMessage());
    }

    @Test
    @DisplayName("Should create Documents object with empty list")
    void shouldCreateDocumentsWithEmptyList() {
        // Given
        List<String> emptyList = Collections.emptyList();

        // When
        Documents documents = Documents.of(emptyList);

        // Then
        assertNotNull(documents);
        assertTrue(documents.documents().isEmpty());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for single invalid URL in list")
    void shouldThrowExceptionForSingleInvalidUrlInList() {
        // Given
        List<String> mixedUrls = List.of(
                "https://valid-url.com",
                "invalid-url",
                "https://another-valid-url.com"
        );

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                Documents.of(mixedUrls)
        );
        assertEquals("Invalid URL format", thrown.getMessage());
    }
} 