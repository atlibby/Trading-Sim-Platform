package com.tradingsim.marketdata.controller;
import com.tradingsim.marketdata.events.PriceTick;
import com.tradingsim.marketdata.service.PriceSimulatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ticks")
public class MarketDataController {
    private final PriceSimulatorService simulatorService;

    public MarketDataController(PriceSimulatorService simulatorService){
        this.simulatorService = simulatorService;
    }

    @GetMapping("/{symbol}")
    public PriceTick getTick(@PathVariable String symbol) {
        return simulatorService.generateTick(symbol);
    }
}

