package com.charity_hub.accounts.internal.shell.firebase;

import com.charity_hub.accounts.internal.core.outputports.IAuthProvider;
import com.charity_hub.accounts.internal.core.exceptions.AppException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class FirebaseAuthProvider implements IAuthProvider {

    private final FirebaseAuth firebaseAuth;
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public FirebaseAuthProvider(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public CompletableFuture<String> getVerifiedMobileNumber(String idToken) {
        return CompletableFuture.supplyAsync(() -> {
            var firebaseToken = verify(idToken).join();
            try {
                var userRecord = firebaseAuth.getUser(firebaseToken.getUid());
                if (userRecord.getPhoneNumber() != null) {
                    return userRecord.getPhoneNumber().replace("+", "");
                } else {
                    log.error("Failed to verify mobile number");
                    throw new AppException.UnAuthorized();
                }
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private CompletableFuture<FirebaseToken> verify(String idToken) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return firebaseAuth.verifyIdToken(idToken);
            } catch (Exception authError) {
                log.error("Failed to verify Id token: {}", idToken, authError);
                throw new AppException.UnAuthorized();
            }
        });
    }
}