package com.tradingsim.tradingapi.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.UUID;

import com.tradingsim.tradingapi.events.OrderCreated;

@Service
public class OrderPublisherService {
    // TODO: inject KafkaTemplate<String, OrderCreated>
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;
    private final OrderCreated event;

    public OrderPublisherService(KafkaTemplate<String, OrderCreated> kafkaTemplate, OrderCreated event){
        this.kafkaTemplate = kafkaTemplate;
        this.event = event;
    }

    public void publishOrderCreated(OrderCreated event){
        // TODO: send event to topic "orders.created"
        UUID orderID = event.getOrderID();

        // TODO: use orderID as message key
        kafkaTemplate.send("orders.created", orderID.toString(), event);

        // TODO: print/log published event for now
        System.out.println("Published order: " + event.getSymbol() + " " + event.getUserID().toString());
    }
}
