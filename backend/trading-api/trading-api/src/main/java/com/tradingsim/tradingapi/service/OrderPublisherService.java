package com.tradingsim.tradingapi.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tradingsim.tradingapi.events.OrderCreated;

@Service
public class OrderPublisherService {
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;

    public OrderPublisherService(KafkaTemplate<String, OrderCreated> kafkaTemplate, OrderCreated event){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreated event){
        String key = event.getOrderID().toString();
        kafkaTemplate.send("orders.created", key, event);
        System.out.println("Published order: " + event.getSymbol() + " " + event.getUserID());
    }
}
