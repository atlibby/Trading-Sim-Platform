package com.tradingsim.tradingapi.controller;
import com.tradingsim.tradingapi.events.OrderCreated;
import com.tradingsim.tradingapi.dto.CreateOrderRequest;
import com.tradingsim.tradingapi.dto.CreateOrderResponse;
import com.tradingsim.tradingapi.service.*;
import com.tradingsim.tradingapi.model.OrderType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.time.Instant;


/**Goal (general):
 * POST api/orders
 * accepts a user order request (CreateOrderRequest)
 * validates it (OrderValidationService object)
 * creates an OrderCreated event (OrderCreated constructor)
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

    public OrderController(OrderPublisherService orderPublisher, OrderValidationService orderValidator){
        this.orderPublisher = orderPublisher;
        this.orderValidator = orderValidator;
    }
    

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
        @RequestBody CreateOrderRequest request 
    ){
        try{
            // validate
            orderValidator.validateRequest(request);

            // generate UUID
            UUID orderID = UUID.randomUUID();
            // normalize symbol
            String normSym = request.getSymbol().trim().toUpperCase();
            // build ordercreated
            OrderCreated event = new OrderCreated(
                orderID,
                request.getUserID(),
                request.getSymbol().trim().toUpperCase(),
                request.getSide(),
                request.getQuantity(),
                OrderType.MARKET,
                Instant.now()
            );
            // publish
            orderPublisher.publishOrderCreated(event);
            // build success response
            // return 202
            CreateOrderResponse response = new CreateOrderResponse(orderID, "Success", "Order accepted, queued for execution");

            return ResponseEntity.ok().body(response);
        } catch(IllegalArgumentException e){
            // build error response
            // return 400
            CreateOrderResponse errorResponse = new CreateOrderResponse(null, "Failure", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
}


