package com.charity_hub.accounts.internal.core.outputports;


import java.util.concurrent.CompletableFuture;

public interface IInvitationRepo {

    CompletableFuture<Boolean> hasInvitation(String mobileNumber);
}