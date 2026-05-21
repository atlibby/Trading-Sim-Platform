// Trading sim platform planning

//* Deliverable 1:
// 1. Order type: implement market orders only first, then add limit later
// 2. Stock symbols: AAPL, NVDA, BIV, VOO, VGX
// 3. Price movement model: implement trend-based first, then volatility profiles per stock later
// 4. Agent output: all of the above
// 5. User roles: trader + admin
// 6. Resume emphasis: AI agent/rec engine, and security architecture  */

//Information system works with:
// class User {
//     UUID id;
//     String username;
//     Role role; // TRADER, ADMIN
// }
// class Stock {
//     String symbol;
//     String name;
//     BigDecimal currentPrice;
// }
// class Order {
//     UUID id;
//     String username;
//     String symbol;
//     OrderSide side; // BUY or SELL
//     OrderType type; // MARKET or LIMIT
//     int quantity;
//     BigDecimal limitPrice;
//     OrderStatus status;
//     Instant createdAt;
// }
// class Trade {
//     UUID id1;
//     String username1;
//     UUID id2;
//     String Username2;
//     String[] symbols1;
//     String[] names1;
//     BigDecimal[] currentPrices1;
//     String[] symbols2;
//     String[] names2;
//     BigDecimal[] currentPrices2;
//     Instant createdAt;
// }
// class PriceTick {
//     String symbol;
//     BigDecimal price;
//     Instant timestamp;
// }
// class Portfolio {
//     Stock[] stocks;
// }
// class Prediction {
//     String symbol;
//     BigDecimel predictedPrice;
//     double confidence;
//     Instant predictionFor;
// }
// class Recommendation {
//     String symbol;
//     Action action; // BUY, HOLD, SELL
//     String reason;
//     double confidence;
//}

//* Pseudocode
// get last N prices for stock
// if recent average price is increasing:
//  recommend BUY
// if recent average price is decreasing:
//  recommend SELL or HOLD
// if price is volatile:
//  lower confidence*/

//* Market data flow:
// Market Data Simulator
// -> publishes PriceTick event to Kafka topic: price.ticks

// Prediction Agent
// -> consumes price.ticks 
// -> stores recent prices
// -> publishes Recommendation event to Kafka topic: agent.recommendation

// Trading API / Frontend
// -> shows latest prices and recommendations*/

//* Order flow:
// User places order through REST API
// -> Trading API validates request
// -> Trading API publishes OrderCreated event to Kafka topic: orders.created

// Matching Engine
// -> consumes orders.created
// -> checks current price
// -> creates TradeExecuted event
// -> stores trade in database*/

//* Deliverable 2:
2A:
1. Trading API service:
- Responsibilities:
  - Accept buy/sell market orders
  - Validate user permissions
  - Publish OrderCreated events to Kafka
  - Return order status
  - Expose REST exndpoints
  - Later: JWT auth, RBAC, throttling

    Possible REST endpoints:
      - POST /api/orders:
        - validate user is TRADER
        - validate stock symbol exists
        - validate quantity > 0
        - create Order object
        - publish OrderCreated event to Kafka
        - return orderID and stsut = PENDING
      - GET /api/orders{id}
      - GET /api/portfolio/{userid}
      - GET /api/stocks
      - GET /api/recommendations 

2. Market Data simulator:
- Responsibilities:
  - Maintain simulated prices for AAPL, NVDA, BIV, VOO, VGX
  - Apply trend-based movement
  - Publish PriceTick events to Kafka every second
  pseudocode:
    every 1 second:
      for each stock:
        trend = getTrend(symbol)
        noise = random small movement
        newPrice = oldPrice + trend + noise
        publish PriceTick(symbol, newPrice, timestamp)
  For now:
    AAPL = mild upward trend
    NVDA = stronger upward trend
    BIV = stable bond-like movement
    VOO = broad market slow upward trend
    VGX = volatile growth-style asset

3. Matching Engine
- Responsibilities:
  - Consume OrderCreated events
  - Look up latest stock price
  - Execute market orders immediately
  - Publish TradExecuted events
  - Update database records
  MVP logic:
    on OrderCreated event:
      currentPrice = getLatestPrice(order.symbol)

      if order.side == BUY:
        tradeValue = currentPrice * quantity
        execute trade

      publish TradeExecuted event

4. Prediction agent
- Responsibilities:
  - Consume PriceTick events
  - Store rolling price history
  - Predict next price
  - Generate BUY/HOLD/SELL recommendation
  - Rank top 3 assets
  - Publish Recommendation events
  MVP prediction logic:
    for each symbol:
      get last N prices
      calculate moving average
      salculate recent slope
      predict next price based on lsope
      assign confidence based on trend strength
      recommend BUY if slope is positive
      recommend SELL if slope is negative
      recommend HOLD if flat or low confidence
    
    Pseudocode:

    history = getLastNPrices(symbol, 10)

    slope = calculateSlope(history)
    predictedPrice = latestPrice + slope

    if slope > threshold:
      action = BUY
    else if slope < -threshold:
      action = SELL
    else:
      action = HOLD
    
    confidence = calculateConfidence(slope, volatility)

    publish Recommendation(symbol, action, predictedPrice, confidence, reason)

2B:
Kafka topics:
  - price.ticks
  - orders.created
  - trades.executed
  - agent.recommendations
  Later ones:
  - orders.rejected
  - risk.alerts
  - portfolio.updated

2C:
Event contracts:

PriceTick event:
{
    "symbol": "AAPL",
    "price": 192.35,
    "timestamp": "2026-05-13T15:20:002"
}

OrderCreated event:
{
    "orderId": "uuid",
    "userId:" "uuid",
    "symbol": "NVDA",
    "side": "BUY",
    "quantity": 10,
    "orderType": "MARKET"
    "timestamp": "2026-05-13T15:20:052"
}

TradeExecuted event:
{
    "tradeId": "uuid",
    "orderId": "uuid",
    "userId": "uuid",
    "symbol": "NVDA",
    "side": "BUY",
    "quantity": 10,
    "executionPrice": 925.50,
    "totalValue": 9255.00,
    "timestamp": "2026-05-13T15:20:062"
}

Recommendation event:
{
    "symbol": "NVDA",
    "action": "BUY",
    "predictedPrice": 934.20,
    "confidence": 0.78,
    "reason": "Recent price trajectory shows upward momentum over the last 10 ticks.",
    "timestamp": "2026-05-13T15:20:072"
}

Deliverable 3:
- trading-api = Java Spring Boot
- market-data-simulator = Java Spring Boot
- prediction-agent = Python Kafka + FastAPI