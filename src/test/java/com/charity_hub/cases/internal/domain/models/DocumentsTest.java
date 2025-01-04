package com.charity_hub.cases.internal.domain.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentsTest {

    @Test
    @DisplayName("Should create Documents object when given valid URLs")
    void should_Create_Documents_With_Valid_Urls() {
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
    void should_Throw_Exception_For_Invalid_Urls() {
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
    void should_Create_Documents_With_Empty_List() {
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
    void should_Throw_Exception_For_Single_Invalid_Url_In_List() {
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
