package com.tradingsim.tradingapi.service;

public class OrderPublisherService {
    // TODO: inject KafkaTemplate<String, OrderCreated>

    public void publishOrderCreated(OrderCreated event){
        // TODO: send event to topic "orders.created"

        // TODO: use orderID as message key

        // TODO: print/log published event for now
    }
}
