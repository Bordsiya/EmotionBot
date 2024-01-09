package org.example.tgbot.service.redis;

public interface MessagePublisher {
    void publish(final String message);
}
