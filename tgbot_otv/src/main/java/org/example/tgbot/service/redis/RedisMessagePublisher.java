package org.example.tgbot.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    private final ChannelTopic topic;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisMessagePublisher(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.topic = new ChannelTopic("emotional");
    }

    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}