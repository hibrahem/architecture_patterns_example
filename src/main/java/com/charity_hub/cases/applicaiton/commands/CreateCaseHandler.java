package com.charity_hub.cases.applicaiton.commands;

import com.charity_hub.cases.domain.models.Case;
import com.charity_hub.cases.domain.contracts.ICaseRepo;
import org.springframework.stereotype.Service;

@Service
public class CreateCaseHandler {

    private ICaseRepo caseRepo;

    public CreateCaseHandler(ICaseRepo caseRepo) {
        this.caseRepo = caseRepo;
    }

    public CaseResponse handle(CreateCase command) {
        int code = caseRepo.nextCode();

        var _case = Case.newCase(code,
                command.title(),
                command.description(),
                command.documents(),
                command.tags(),
                command.status(),
                command.goal());

        caseRepo.save(_case);
        return new CaseResponse(_case.code());
    }
}
