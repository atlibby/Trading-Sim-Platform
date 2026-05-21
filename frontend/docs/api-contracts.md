PriceTick event
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
