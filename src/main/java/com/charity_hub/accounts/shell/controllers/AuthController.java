package com.charity_hub.accounts.shell.controllers;

import com.charity_hub.accounts.core.inputports.AccountsServicePort;
import com.charity_hub.accounts.core.services.Authenticate;
import com.charity_hub.accounts.shell.controllers.common.DeferredResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AuthController {
    private final AccountsServicePort accountsServicePort;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public AuthController(AccountsServicePort accountsServicePort) {
        this.accountsServicePort = accountsServicePort;
    }

    @PostMapping("/v1/accounts/authenticate")
    public DeferredResult<ResponseEntity<?>> login(@RequestBody Authenticate authenticate) {
        log.info("Processing authentication request");
        return DeferredResults.from(
                accountsServicePort.authenticate(authenticate)
                        .thenApply(ResponseEntity::ok)
        );
    }
}

