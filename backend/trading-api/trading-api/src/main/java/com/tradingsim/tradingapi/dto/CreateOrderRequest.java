package com.tradingsim.tradingapi.dto;
import java.util.UUID;

import com.tradingsim.tradingapi.model.OrderSide;

public class CreateOrderRequest {
    UUID userID;
    String symbol;
    OrderSide side;
    int quantity;
}
