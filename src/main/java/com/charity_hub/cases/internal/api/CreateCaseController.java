package com.charity_hub.cases.internal.api;

import com.charity_hub.cases.internal.applicaiton.commands.CaseResponse;
import com.charity_hub.cases.internal.applicaiton.commands.CreateCase;
import com.charity_hub.cases.internal.applicaiton.commands.CreateCaseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCaseController {

    private final CreateCaseHandler createCaseHandler;

    public CreateCaseController(CreateCaseHandler createCaseHandler) {
        this.createCaseHandler = createCaseHandler;
    }

    @PostMapping("/v1/cases")
    public ResponseEntity<?> createCase(@RequestBody CreateCase command) {
        CaseResponse response = createCaseHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
