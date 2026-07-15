package com.tradingsim.tradingapi.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tradingsim.tradingapi.dto.CreateOrderRequest;

//*
// Goal
// - symbol is supported
// - quantity is greater than 0
// - side is present */

@Service
public class OrderValidationService {
    private final List<String> SUPPORTED_SYMBOLS = List.of("AAPL", "NVDA", "BIV", "VOO", "VGX");

        public void validateRequest(CreateOrderRequest request){
        String symbol = request.getSymbol();

        if (symbol == null || symbol.isBlank()){
            throw new IllegalArgumentException("Order must include symbol");
        }

        String normalizedSymbol = symbol.trim().toUpperCase();

        if (!SUPPORTED_SYMBOLS.contains(normalizedSymbol)){
            throw new IllegalArgumentException("Symbol must be of existing stock");
        }

        if (request.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (request.getUserID() == null){
            throw new IllegalArgumentException("User ID must be included in order");
        }

        if (request.getSide() == null){
            throw new IllegalArgumentException("Side (BUY/SELL) must be included in order");
        }
        
    }
}
