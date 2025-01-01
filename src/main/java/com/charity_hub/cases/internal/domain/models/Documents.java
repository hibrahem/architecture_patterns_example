package com.charity_hub.cases.internal.domain.models;

import java.util.List;
import java.util.regex.Pattern;

public record Documents(List<String> documents) {

    static Documents of(List<String> documents) {

        Pattern compile = Pattern.compile("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)");
        for (String document : documents) {
            if (!compile.matcher(document).matches()) {
                throw new IllegalArgumentException("Invalid URL format");
            }
        }
        return new Documents(documents);
    }
}
