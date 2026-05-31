package com.unq.mitvu.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "emails.faltas.queue";
    public static final String EXCHANGE_NAME = "emails.exchange";
    public static final String ROUTING_KEY = "emails.faltas.routingKey";

    public static final String QUEUE_ANUNCIOS = "emails.anuncios.queue";
    public static final String ROUTING_KEY_ANUNCIOS = "emails.anuncios.routingKey";

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue anunciosQueue() {
        return new Queue(QUEUE_ANUNCIOS, true);
    }

    @Bean
    public Binding bindingAnuncios(Queue anunciosQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(anunciosQueue).to(emailExchange).with(ROUTING_KEY_ANUNCIOS);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}