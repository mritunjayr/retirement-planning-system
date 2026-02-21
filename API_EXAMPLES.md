# Sample API Requests for Testing

## 1. Transaction Parser
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/transactions:parse \
  -H "Content-Type: application/json" \
  -d '[
    {"date": "2023-10-12 20:15:30", "amount": 250},
    {"date": "2023-02-28 15:49:20", "amount": 375},
    {"date": "2023-07-01 21:59:00", "amount": 620},
    {"date": "2023-12-17 08:09:45", "amount": 480}
  ]'
```

## 2. Transaction Validator
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/transactions:validator \
  -H "Content-Type: application/json" \
  -d '{
    "wage": 50000,
    "transactions": [
      {"date": "2023-01-15 10:30:00", "amount": 2000, "ceiling": 2100, "remanent": 100},
      {"date": "2023-03-15 14:45:00", "amount": 3500, "ceiling": 3600, "remanent": 100},
      {"date": "2023-06-10 09:15:00", "amount": 1500, "ceiling": 1500, "remanent": 0},
      {"date": "2023-07-10 09:15:00", "amount": -250, "ceiling": 0, "remanent": 0}
    ]
  }'
```

## 3. Temporal Constraints Filter
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/transactions:filter \
  -H "Content-Type: application/json" \
  -d '{
    "q": [{"fixed": 0, "start": "2023-07-01 00:00:00", "end": "2023-07-31 23:59:59"}],
    "p": [{"extra": 25, "start": "2023-10-01 08:00:00", "end": "2023-12-31 19:59:59"}],
    "k": [{"start": "2023-03-01 00:00:00", "end": "2023-11-30 23:59:59"}],
    "transactions": [
      {"date": "2023-03-15 10:30:00", "amount": 375},
      {"date": "2023-07-15 14:45:00", "amount": 620},
      {"date": "2023-10-12 20:15:00", "amount": 250},
      {"date": "2023-12-17 08:09:00", "amount": 480}
    ]
  }'
```

## 4. NPS Returns Calculation
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/returns:nps \
  -H "Content-Type: application/json" \
  -d '{
    "age": 29,
    "wage": 50000,
    "inflation": 5.5,
    "q": [{"fixed": 0, "start": "2023-07-01 00:00:00", "end": "2023-07-31 23:59:59"}],
    "p": [{"extra": 25, "start": "2023-10-01 08:00:00", "end": "2023-12-31 19:59:59"}],
    "k": [
      {"start": "2023-03-01 00:00:00", "end": "2023-11-30 23:59:59"},
      {"start": "2023-01-01 00:00:00", "end": "2023-12-31 23:59:59"}
    ],
    "transactions": [
      {"date": "2023-10-12 20:15:00", "amount": 250},
      {"date": "2023-02-28 15:49:00", "amount": 375},
      {"date": "2023-07-01 21:59:00", "amount": 620},
      {"date": "2023-12-17 08:09:00", "amount": 480}
    ]
  }'
```

## 5. Index Fund Returns Calculation
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/returns:index \
  -H "Content-Type: application/json" \
  -d '{
    "age": 29,
    "wage": 50000,
    "inflation": 5.5,
    "q": [{"fixed": 0, "start": "2023-07-01 00:00:00", "end": "2023-07-31 23:59:59"}],
    "p": [{"extra": 25, "start": "2023-10-01 08:00:00", "end": "2023-12-31 19:59:59"}],
    "k": [
      {"start": "2023-03-01 00:00:00", "end": "2023-11-30 23:59:59"},
      {"start": "2023-01-01 00:00:00", "end": "2023-12-31 23:59:59"}
    ],
    "transactions": [
      {"date": "2023-10-12 20:15:00", "amount": 250},
      {"date": "2023-02-28 15:49:00", "amount": 375},
      {"date": "2023-07-01 21:59:00", "amount": 620},
      {"date": "2023-12-17 08:09:00", "amount": 480}
    ]
  }'
```

## 6. Performance Metrics
```bash
curl -X GET http://localhost:5477/blackrock/challenge/v1/performance
```
