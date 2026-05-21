package com.tradingsim.marketdata.events;

import java.math.BigDecimal;
import java.time.Instant;

public class PriceTick {
    private String symbol;
    private BigDecimal price;
    private Instant timestamp;

    public PriceTick(){
    }

    public PriceTick(String symbol, BigDecimal price, Instant timestamp){
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getSymbol(){
        return symbol;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public Instant getTimeStamp(){
        return timestamp;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setTimeStamp(Instant timestamp){
        this.timestamp = timestamp;
    }

}


