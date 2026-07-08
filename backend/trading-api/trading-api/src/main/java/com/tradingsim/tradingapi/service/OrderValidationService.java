package com.tradingsim.tradingapi.service;
import com.tradingsim.tradingapi.dto.CreateOrderRequest;
import com.tradingsim.tradingapi.dto.CreateOrderResponse;
import com.tradingsim.tradingapi.model.OrderSide;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//*
// Goal
// - symbol is supported
// - quantity is greater than 0
// - side is present */

@Service
public class OrderValidationService {
    private CreateOrderRequest request;
    private final List<String> SUPPORTED_SYMBOLS = List.of("AAPL", "NVDA", "BIV", "VOO", "VGX");

    public OrderValidationService(CreateOrderRequest request){
        this.request = request;
    }

    @GetMapping("/request")
    public ResponseEntity<CreateOrderResponse> validateRequest(@RequestParam("quantity") int quantity, @RequestParam("userID") UUID userID, @RequestParam("side") OrderSide side){
        CreateOrderResponse response = new CreateOrderResponse();

        response.setStatus("Order placed");
        response.setMessage("Info: " + request);
        
        if (quantity <= 0){
            response.setStatus("Error");
            response.setMessage("Quantity must be greater than 0");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userID == null){
            response.setStatus("Error");
            response.setMessage("User ID is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (side == null){
            response.setStatus("Error");
            response.setMessage("Order side required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
