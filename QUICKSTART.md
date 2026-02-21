# Quick Start Guide

## Prerequisites
- Java 21 installed
- Maven installed (or use included mvnw.cmd)
- Docker installed (for containerization)

## Option 1: Run Locally (Fastest)

### Step 1: Build the application
```bash
mvn clean package -DskipTests
```

### Step 2: Run the application
```bash
java -jar target/retirement-planning-system-0.0.1-SNAPSHOT.jar
```

### Step 3: Test an endpoint
```bash
curl -X POST http://localhost:5477/blackrock/challenge/v1/transactions:parse ^
  -H "Content-Type: application/json" ^
  -d "[{\"date\": \"2023-10-12 20:15:30\", \"amount\": 250}]"
```

Expected output:
```json
[{"date":"2023-10-12 20:15:30","amount":250.0,"ceiling":300.0,"remanent":50.0}]
```

## Option 2: Run with Docker

### Step 1: Build the JAR
```bash
mvn clean package -DskipTests
```

### Step 2: Build Docker image
```bash
docker build -t blk-hacking-ind-mritunjay-kumar .
```

### Step 3: Run Docker container
```bash
docker run -d -p 5477:5477 --name retirement-app blk-hacking-ind-mritunjay-kumar
```

### Step 4: Check logs
```bash
docker logs retirement-app
```

### Step 5: Test endpoint
```bash
curl -X GET http://localhost:5477/blackrock/challenge/v1/performance
```

## All Endpoints

1. **Parse Transactions**: `POST /blackrock/challenge/v1/transactions:parse`
2. **Validate Transactions**: `POST /blackrock/challenge/v1/transactions:validator`
3. **Filter Transactions**: `POST /blackrock/challenge/v1/transactions:filter`
4. **NPS Returns**: `POST /blackrock/challenge/v1/returns:nps`
5. **Index Returns**: `POST /blackrock/challenge/v1/returns:index`
6. **Performance**: `GET /blackrock/challenge/v1/performance`

See `API_EXAMPLES.md` for detailed request examples.

## Troubleshooting

### Port already in use
```bash
# Windows
netstat -ano | findstr :5477
taskkill /PID <PID> /F
```

### Docker container not starting
```bash
docker logs retirement-app
```

### Application not responding
Check if the application started successfully:
```bash
curl http://localhost:5477/blackrock/actuator/health
```

## Stop the Application

### Local
Press `Ctrl+C` in the terminal

### Docker
```bash
docker stop retirement-app
docker rm retirement-app
```

## Development Mode

For development with auto-reload:
```bash
mvn spring-boot:run
```

## Health Check

```bash
curl http://localhost:5477/blackrock/actuator/health
```

Expected: `{"status":"UP"}`
