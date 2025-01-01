package com.charity_hub.accounts.internal.shell.firebase;

import com.google.firebase.messaging.*;

import java.util.List;

public class FCMService {

    public void sendToDevices(List<String> tokens, String title, String body) {
        for (String token : tokens) {
            Message message = buildMessage(title, body)
                    .setToken(token)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendToTopic(String topic, String event, String extraJsonData, String title, String body) {
        Message message = buildMessage(title, body)
                .setTopic(topic)
                .putData("topic", topic)
                .putData("event", event)
                .putData("data", extraJsonData)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Subscribe tokens to a topic.
     *
     * @param topic  The topic to subscribe to
     * @param tokens The list of tokens to subscribe
     */
    public void subscribeToTopic(String topic, List<String> tokens) {
        TopicManagementResponse response;
        try {
            response = FirebaseMessaging.getInstance().subscribeToTopic(tokens, topic);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
    }

    private Message.Builder buildMessage(String title, String body) {
        Message.Builder builder = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build());
        androidConfig(builder);
        apnsConfig(builder);
        return builder;
    }

    private void apnsConfig(Message.Builder builder) {
        builder.setApnsConfig(
                ApnsConfig.builder()
                        .setAps(Aps.builder().build())
                        .build()
        );
    }

    private void androidConfig(Message.Builder builder) {
        builder.setAndroidConfig(
                AndroidConfig.builder()
                        .setTtl(3600 * 1000L)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("stock_ticker_update")
                                .setColor("#f45342")
                                .build()
                        ).build()
        );
    }
}