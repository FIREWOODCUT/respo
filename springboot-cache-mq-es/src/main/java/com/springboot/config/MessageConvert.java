package com.springboot.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息获取为json
 */
@Configuration
public class MessageConvert {
    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
