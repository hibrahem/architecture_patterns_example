package com.charity_hub.cases.domain.contracts;

import com.charity_hub.cases.domain.models.CaseEvent;
import com.charity_hub.cases.domain.models.CaseOpened;
import com.charity_hub.shared.IEventBus;
import org.springframework.stereotype.Component;

@Component
public class CasePublisher {
    private final IEventBus eventBus;

    public CasePublisher(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publish(CaseEvent caseEvent) {
        if (caseEvent instanceof CaseOpened event) {
            var integrationEvent = new CaseOpenedDto(event.caseCode(), event.title(), event.description());
            eventBus.push(integrationEvent);
        }
    }
}
