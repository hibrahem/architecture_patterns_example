package com.charity_hub.cases.internal.applicaiton.commands;

import java.util.List;

public record CreateCase(
        String title,
        String description,
        int goal,
        String status,
        List<String> documents,
        List<String> tags
) {
}
