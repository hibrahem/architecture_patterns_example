package com.charity_hub.api;

import com.charity_hub.api.common.DeferredResults;
import com.charity_hub.application.authentication.Authenticate;
import com.charity_hub.application.authentication.IAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AuthController {
    private final IAuthenticationService authenticationService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public AuthController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/v1/accounts/authenticate")
    public DeferredResult<ResponseEntity<?>> login(@RequestBody Authenticate authenticate) {
        log.info("Processing authentication request");
        return DeferredResults.from(
                authenticationService.authenticate(authenticate)
                        .thenApply(ResponseEntity::ok)
        );
    }
}

