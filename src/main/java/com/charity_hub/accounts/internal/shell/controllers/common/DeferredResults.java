package com.charity_hub.accounts.internal.shell.controllers.common;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class DeferredResults<T> {

    private DeferredResults() {}

    public static <T> DeferredResult<T> from(final CompletableFuture<T> future) {
        final DeferredResult<T> deferred = new DeferredResult<>();
        future.thenAccept(deferred::setResult);
        future.exceptionally(ex -> {
            if (ex instanceof CompletionException) {
                deferred.setErrorResult(ex.getCause());
            } else {
                deferred.setErrorResult(ex);
            }
            return null;
        });
        return deferred;
    }
}