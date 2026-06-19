package com.tradingsim.tradingapi.dto;
import java.util.UUID;


public class CreateOrderResponse {
    private UUID orderID;
    private String status; // TODO: Consider changing data type later
    private String message; // TODO: Implement small NLP component to this later

    public CreateOrderResponse(){}

    public CreateOrderResponse(UUID orderID, String status, String message){
        this.orderID = orderID;
        this.status = status;
        this.message = message;
    }

    public UUID getOrderID(){
        return orderID;
    }

    public String getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public void setOrderID(UUID orderID){
        this.orderID = orderID;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
