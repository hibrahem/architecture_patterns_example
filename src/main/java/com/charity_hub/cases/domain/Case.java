package com.charity_hub.cases.domain;

import java.util.List;

public class Case {
    private final String code;
    private final String title;
    private final String description;
    private final Documents documents;
    private final List<String> tags;
    private final List<Donation> donations;
    private int goal;

    public Case(String code,
                String title,
                String description,
                List<String> documents,
                List<String> tags,
                List<Donation> donations,
                int goal) {

        this.code = code;
        this.title = title;
        this.description = description;
        this.documents = Documents.of(documents);
        this.tags = tags;
        this.donations = donations;
        this.goal = goal;
    }
}
