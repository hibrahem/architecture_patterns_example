package com.charity_hub.accounts.internal.core.outputports;

import java.util.concurrent.CompletableFuture;

public interface IAuthProvider {
    CompletableFuture<String> getVerifiedMobileNumber(String idToken);
}