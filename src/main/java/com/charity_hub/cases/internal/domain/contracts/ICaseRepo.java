package com.charity_hub.cases.internal.domain.contracts;

import com.charity_hub.cases.internal.domain.models.Case;

public interface ICaseRepo {
    int nextCode();

    void save(Case aCase);
}
