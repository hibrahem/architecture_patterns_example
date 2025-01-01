package com.charity_hub.shared;

import java.util.concurrent.CompletableFuture;

public interface IEventBus {
    <T> void subscribe(Object owner, Class<T> event, EventCallback<T> callback);

    void unsubscribe(Object owner);

    <T> CompletableFuture<Void> push(T event);

    @FunctionalInterface
    public interface EventCallback<T> {
        CompletableFuture<Void> handle(T event);
    }
}
