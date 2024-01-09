package org.example.tgbot.configuration;

import org.example.tgbot.bot.ExchangeRatesBot;
import org.example.tgbot.repository.EmotionStatisticRepository;
import org.example.tgbot.service.redis.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisInputConfiguration {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    MessageListenerAdapter messageListener(ExchangeRatesBot exchangeRatesBot, EmotionStatisticRepository emotionStatisticRepository) {
        return new MessageListenerAdapter(new RedisMessageSubscriber(exchangeRatesBot, emotionStatisticRepository));
    }

    @Bean
    RedisMessageListenerContainer redisContainer(ExchangeRatesBot exchangeRatesBot, EmotionStatisticRepository emotionStatisticRepository) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(messageListener(exchangeRatesBot, emotionStatisticRepository), new ChannelTopic("result"));
        return container;
    }
}
