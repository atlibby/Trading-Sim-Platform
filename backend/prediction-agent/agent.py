"""
prediction-agent consumes price.ticks from Kafka
stores recent prices in memory
calculates trend/slope
publishes recommendations to agent.recommendations
"""

from collections import defaultdict, deque
import json
from kafka import KafkaConsumer, KafkaProducer

price_history = defaultdict(lambda: deque(maxlen=10))

def consume_price_ticks():
    # connect to Kafka topic price.ticks
    consumer = KafkaConsumer(
        'price.ticks', 
        bootstrap_servers='localhost:9092',
        value_deserializer=lambda m: json.loads(m.decode("utf-8")),
        auto_offset_reset="latest",
        enable_auto_commit=True,
        group_id="prediction-agent"
        )
    for msg in consumer:
        data = msg.value
        symbol = data['symbol']
        price = float(data['price'])
        print(f"{symbol}: {price} | history size: {len(price_history[symbol])}")
        price_history[symbol].append(price)
        if len(price_history[symbol]) == 10:
            recommendation = calculate_recommendation(symbol, price_history[symbol])
            print("Recommendation: ", recommendation)
            publish_recommendation(recommendation)

def publish_recommendation(recommendation):
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    producer.send('agent.recommendations', json.dumps(recommendation).encode('utf-8'))
    producer.flush()

def calculate_recommendation(symbol, prices):
    latest_price = prices[-1]
    first_price = prices[0]
    slope = latest_price - first_price
    predicted_price = latest_price + slope / len(prices)
    threshold = 0.5
    confidence = min(abs(slope) / 5.0, 1.0)
    if slope > threshold:
        action = "BUY"
    elif slope < -threshold:
        action = "SELL"
    else:
        action = "HOLD"

    return {
        'symbol': symbol,
        'action': action,
        'predictedPrice': round(predicted_price),
        'confidence': round(confidence, 2),
        'reason': f'Recent 10-tick trajectory produced slope {round(slope, 2)}.'
    }

if __name__ == '__main__':
    # test_prices = [100, 100.4, 100.8, 101.1, 101.6, 102.0, 102.4, 102.8, 103.1, 103.6]
    # print(calculate_recommendation("AAPL", test_prices))
    consume_price_ticks()
