package com.tradingsim.tradingapi.dto;
import java.util.UUID;


public class CreateOrderResponse {
    private UUID orderID;
    private String status; // TODO: Consider changing data type later
    private String message;

    public CreateOrderResponse(){}

    public CreateOrderResponse(UUID orderID, String status, String message){
        this.orderID = orderID;
        this.status = status;
        this.message = message;
    }

    
}
