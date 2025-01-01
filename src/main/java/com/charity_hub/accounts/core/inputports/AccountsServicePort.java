package com.charity_hub.accounts.core.inputports;

import com.charity_hub.accounts.core.services.Authenticate;
import com.charity_hub.accounts.core.services.AuthenticateResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface AccountsServicePort {
    CompletableFuture<AuthenticateResponse> authenticate(Authenticate command);
}