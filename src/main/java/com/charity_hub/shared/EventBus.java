package com.charity_hub.shared;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class EventBus implements IEventBus {
    private final List<EventListener<?>> listeners = new ArrayList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public <T> void subscribe(Object owner, Class<T> event, EventCallback<T> callback) {
        EventListener<T> listener = new EventListener<>(owner, event, callback);
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    @Override
    public void unsubscribe(Object owner) {
        synchronized (listeners) {
            listeners.removeIf(listener -> listener.owner().equals(owner));
        }
    }

    @Override
    public <T> CompletableFuture<Void> push(T event) {
        return CompletableFuture.runAsync(() -> {
            synchronized (listeners) {
                listeners.forEach(listener -> {
                    if (listener.event().isInstance(event)) {
                        @SuppressWarnings("unchecked")
                        EventListener<T> typedListener = (EventListener<T>) listener;
                        typedListener.callback().handle(event);
                    }
                });
            }
        }, executor);
    }

    // Method to properly shutdown the executor
    public void shutdown() {
        executor.shutdown();
    }
}

record EventListener<T>(Object owner, Class<T> event, IEventBus.EventCallback<T> callback) {
}


