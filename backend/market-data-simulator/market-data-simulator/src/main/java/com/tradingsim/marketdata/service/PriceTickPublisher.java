package com.tradingsim.marketdata.service;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tradingsim.marketdata.events.PriceTick;

@Service
public class PriceTickPublisher {
    private final KafkaTemplate<String, PriceTick> kafkaTemplate;
    private final PriceSimulatorService simulatorService;

    private final List<String> symbols = List.of("AAPL", "NVDA", "BIV", "VOO", "VGX");

    public PriceTickPublisher(KafkaTemplate<String, PriceTick> kafkaTemplate, PriceSimulatorService simulatorService){
        this.kafkaTemplate = kafkaTemplate;
        this.simulatorService = simulatorService;
    }

    @Scheduled(fixedRate = 1000)
    public void publishTicks() {
        for (String symbol : symbols){
            PriceTick tick = simulatorService.generateTick(symbol);
            kafkaTemplate.send("price.ticks", symbol, tick);
            System.out.println("Published tick: " + tick.getSymbol() + " " + tick.getPrice());
        }
    }
}
