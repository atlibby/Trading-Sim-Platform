package com.tradingsim.tradingapi.controller;
import com.tradingsim.tradingapi.events.OrderCreated;
import com.tradingsim.tradingapi.dto.*;
import com.tradingsim.tradingapi.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

/**Goal (general):
 * POST api/orders
 * accepts a user order request (CreateOrderRequest)
 * validates it (OrderValidationService object)
 * creates an OrderCreated event (function that uses OrderCreated.java methods)
 * publishes it to Kafka (OrderPublisherService.java)
 * returns a pending response
 * Receive request body (CreateOrderRequest object/helper function)
 * Validate request (OrderValidationService object/helper function)
 * Generate orderID 
 * Publish event using OrderPublisherService
 * return CreateOrderResponse
 */

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderPublisherService orderPublisher;
    private final OrderValidationService orderValidator;

    private final CreateOrderRequest orderRequest;

    public OrderController(OrderPublisherService orderPublisher, OrderValidationService orderValidator, CreateOrderRequest orderRequest){
        this.orderPublisher = orderPublisher;
        this.orderValidator = orderValidator;
        this.orderRequest = orderRequest;
    }

    public boolean validateRequest(CreateOrderRequest orderRequest){
        // TODO: inject validation service logic here
        OrderValidationService orderValidator;
    }
}
