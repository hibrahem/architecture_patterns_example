package com.charity_hub.application.authentication;

import java.util.concurrent.CompletableFuture;


import org.springframework.stereotype.Service;
@Service
public interface IAuthenticationService {
    CompletableFuture<AuthenticateResponse> authenticate(Authenticate command);
}
