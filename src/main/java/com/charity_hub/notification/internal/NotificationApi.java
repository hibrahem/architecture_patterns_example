package com.charity_hub.notification.internal;

import com.charity_hub.notification.INotificationApi;
import org.springframework.stereotype.Component;

@Component
public class NotificationApi implements INotificationApi {

    @Override
    public void notify(Object payload, String topicName) {
        System.out.println("Notifying topic: " + topicName + " with payload: " + payload);
    }
}
