package com.tradingsim.tradingapi.service;
import com.tradingsim.tradingapi.dto.CreateOrderRequest;
import com.tradingsim.tradingapi.model.OrderSide;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public boolean validateRequest(CreateOrderRequest request){
        if (!SUPPORTED_SYMBOLS.contains(request.getSymbol())){
            throw new IllegalArgumentException("Unsupported symbol");
        }

        if (request.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (request.getUserID() == null){
            throw new IllegalArgumentException("User ID is required");
        }

        if (request.getSide() == null){
            throw new IllegalArgumentException("Order side required");
        }

        return true;
    }
}
