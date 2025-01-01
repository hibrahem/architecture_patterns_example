package com.charity_hub.cases.internal.infrastructure.eventhandlers;

import com.charity_hub.cases.internal.infrastructure.CaseOpenedDto;
import com.charity_hub.notification.INotificationApi;
import com.charity_hub.shared.IEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CaseOpenedEventHandler {
    private IEventBus eventBus;
    private INotificationApi notificationApi;

    public CaseOpenedEventHandler(IEventBus eventBus, INotificationApi notificationApi) {
        this.eventBus = eventBus;
        this.notificationApi = notificationApi;
    }

    @Bean
    public void subscribe() {
        eventBus.subscribe(this, CaseOpenedDto.class, this::handle);
    }

    private CompletableFuture<Void> handle(CaseOpenedDto caseOpenedDto) {
        System.out.println("Case opened: " + caseOpenedDto.title());
        notificationApi.notify(caseOpenedDto, "case.opened");
        return null;
    }
}
