package com.tradingsim.tradingapi.dto;
import java.util.UUID;

import com.tradingsim.tradingapi.model.OrderSide;

public class CreateOrderRequest {
    private UUID userID;
    private String symbol;
    private OrderSide side;
    private int quantity;

    public CreateOrderRequest(){}

    public CreateOrderRequest(UUID userID, String symbol, OrderSide side, int quantity){
        this.userID = userID;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
    }

    public UUID getUserID(){
        return userID;
    }

    public String getSymbol(){
        return symbol;
    }

    public OrderSide getSide(){
        return side;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setSide(OrderSide side){
        this.side = side;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}


