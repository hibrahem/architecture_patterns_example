package com.charity_hub.cases.domain.contracts;

import com.charity_hub.cases.domain.models.Case;

public interface ICaseRepo {
    int nextCode();

    void save(Case aCase);
}
