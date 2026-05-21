package com.tradingsim.tradingapi.events;
import java.time.Instant;
import java.util.UUID;

import com.tradingsim.tradingapi.model.OrderSide;
import com.tradingsim.tradingapi.model.OrderType;

public class OrderCreated {
    private UUID orderID;
    private UUID userID;
    private String symbol;
    private OrderSide side;
    private int quantity;
    private OrderType orderType;
    private Instant timestamp;

    public OrderCreated(){}

    public OrderCreated(UUID orderID, UUID userID, String symbol, OrderSide side, int quantity, OrderType orderType, Instant timestamp){
        this.orderID = orderID;
        this.userID = userID;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.orderType = orderType;
        this.timestamp = timestamp;
    }

    public UUID getOrderID(){
        return orderID;
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

    public OrderType getOrderType(){
        return orderType;
    }

    public Instant getTimestamp(){
        return timestamp;
    }

    public void setSide(OrderSide side){
        this.side = side;
    }
}
