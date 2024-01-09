package org.example.tgbot.configuration;

import org.example.tgbot.service.redis.MessagePublisher;
import org.example.tgbot.service.redis.RedisMessagePublisher;
import org.example.tgbot.service.redis.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@Configuration
public class RedisOutputConfiguration {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate);
    }
}