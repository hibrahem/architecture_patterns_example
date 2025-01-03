package com.charity_hub.cases.internal.domain.models.fixtures;

import com.charity_hub.cases.internal.domain.models.Case;

import java.util.List;

public class CaseFixture {
    public static final int CODE = 12345;
    public static final String TITLE = "Test Case";
    public static final String DESCRIPTION = "Test Description";
    public static final List<String> DOCUMENTS = List.of(
            "https://docs.example.com/doc1.pdf",
            "https://docs.example.com/doc2.pdf"
    );
    public static final List<String> TAGS = List.of("tag1", "tag2");
    public static final String STATUS_OPENED = "OPENED";
    public static final String STATUS_CLOSED = "CLOSED";
    public static final int GOAL = 1000;

    public static Case createDefault() {
        return Case.newCase(
                CODE,
                TITLE,
                DESCRIPTION,
                DOCUMENTS,
                TAGS,
                STATUS_OPENED,
                GOAL
        );
    }

    public static Case createWithStatus(String status) {
        return Case.newCase(
                CODE,
                TITLE,
                DESCRIPTION,
                DOCUMENTS,
                TAGS,
                status,
                GOAL
        );
    }
} 
