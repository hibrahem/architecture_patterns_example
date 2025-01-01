package com.charity_hub.cases.infrastructure;

import com.charity_hub.cases.domain.contracts.ICaseRepo;
import com.charity_hub.cases.domain.models.Case;
import org.springframework.stereotype.Repository;

@Repository
public class CaseRepo implements ICaseRepo {
    private final CasePublisher casePublisher;

    public CaseRepo(CasePublisher casePublisher) {
        this.casePublisher = casePublisher;
    }


    @Override
    public int nextCode() {
        return 1;
    }

    @Override
    public void save(Case aCase) {
        System.out.println("Case saved");
        aCase.getOccurredEvents().forEach(casePublisher::publish);
    }
}