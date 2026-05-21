package com.tradingsim.marketdata.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tradingsim.marketdata.events.PriceTick;

import jakarta.annotation.PostConstruct;

//* Goal of file:
// 1. maintain current prices
// 2. apply new trends + randomness
// 3. generate new PriceTick events
// Stateful service, keeps currentPrices in memory */

@Service
public class PriceSimulatorService{
    private final Map<String, BigDecimal> currentPrices = new HashMap<>();

    public PriceSimulatorService(){}

    @PostConstruct
    public void initializePrices(){
        currentPrices.put("AAPL", new BigDecimal("150.00")); // mild upward trend
        currentPrices.put("NVDA", new BigDecimal("280.00")); // strong upward trend
        currentPrices.put("BIV", new BigDecimal("700.00")); // stable bond-like trend
        currentPrices.put("VOO", new BigDecimal("450.00")); // broad market slow upward trend
        currentPrices.put("VGX", new BigDecimal("600.00")); // volatile growth-style trend
    }

    public PriceTick generateTick(String symbol){
        // get price
        BigDecimal currentPrice = currentPrices.get(symbol);

        // apply trend + noise
        BigDecimal priceTrend = getTrend(symbol);
        BigDecimal noise = randomNoise();

        // calculate new price
        BigDecimal newPrice = currentPrice.multiply(BigDecimal.ONE.add(priceTrend)).add(noise);
        newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);
        return new PriceTick(symbol, newPrice, Instant.now());
    }

    private BigDecimal getTrend(String symbol){
        return switch (symbol) {
            case "AAPL" -> new BigDecimal("0.05");
            case "NVDA" -> new BigDecimal("0.10");
            case "BIV" -> new BigDecimal("0.01");
            case "VOO" -> new BigDecimal("0.03");
            case "VGX" -> new BigDecimal("0.07");
            default -> BigDecimal.ZERO;
        }; 
    }

    private BigDecimal randomNoise(){
        Random r = new Random();
        return BigDecimal.valueOf(r.nextDouble());
    }
}