package com.charity_hub.cases.domain.models;

public record CaseOpened(int caseCode, String title, String description) implements CaseEvent {

    public static CaseOpened of(Case aCase) {
        return new CaseOpened(aCase.code(), aCase.title(), aCase.description());
    }
}
