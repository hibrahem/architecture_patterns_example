package com.charity_hub.cases.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Case {
    private final int code;
    private final String title;
    private final String description;
    private final Documents documents;
    private final List<String> tags;
    private final List<Donation> donations;
    private int goal;
    private CaseStatus status;

    private List<CaseEvent> occurredEvents = List.of();

    public List<CaseEvent> getOccurredEvents() {
        var events = new ArrayList<>(this.occurredEvents);
        this.occurredEvents.clear();
        return events;
    }

    private Case(int code,
                 String title,
                 String description,
                 List<String> documents,
                 List<String> tags,
                 List<Donation> donations,
                 CaseStatus status,
                 int goal) {

        this.code = code;
        this.title = title;
        this.description = description;
        this.documents = Documents.of(documents);
        this.tags = tags;
        this.donations = donations;
        this.status = status;
        this.goal = goal;

    }

    public static Case newCase(int code,
                               String title,
                               String description,
                               List<String> documents,
                               List<String> tags,
                               String status,
                               int goal) {
        var aCase = new Case(code,
                title,
                description,
                documents,
                tags,
                new ArrayList<>(),
                CaseStatus.valueOf(status),
                goal);
        if (aCase.status == CaseStatus.OPENED) {
            aCase.occurredEvents.add(CaseOpened.of(aCase));
        }
        return aCase;
    }

    public int code() {
        return code;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }
}
