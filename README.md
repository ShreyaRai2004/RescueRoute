# RescueRoute — Complete Emergency Dispatch & Route Optimization System

RescueRoute is a complete placement-oriented Java project for prioritizing emergencies, selecting an eligible response vehicle, calculating a shortest route, dispatching the unit safely, and tracking the operational state.

## Project structure

```text
RescueRoutePackage/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/rescueroute/
│       │   ├── algorithm/       # Graph + Dijkstra
│       │   ├── config/          # Seed data + CORS
│       │   ├── controller/      # REST API
│       │   ├── dto/             # Request/response models
│       │   ├── entity/          # JPA entities
│       │   ├── enums/           # Domain states/types
│       │   ├── exception/       # API error handling
│       │   ├── repository/      # PostgreSQL/JPA access
│       │   └── service/         # Business logic
│       └── test/                # JUnit tests
└── frontend/
    ├── index.html
    ├── styles.css
    └── app.js
```

## Technology stack

- Java 17
- Spring Boot 3.4.10
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- JUnit 5 + Spring Boot Test
- HTML5 / CSS3 / Vanilla JavaScript
- Leaflet + OpenStreetMap

## Main features

- Emergency incident creation and status management
- Severity-based priority ordering
- Java `PriorityQueue` for deterministic emergency ordering
- Vehicle eligibility filtering
- Nearest eligible vehicle selection
- Workload tie-breaker
- Explicit weighted graph
- Manual Dijkstra shortest-path implementation
- ETA calculation from configured average speed
- Vehicle state lifecycle
- PostgreSQL persistence
- Transactional dispatch
- Pessimistic locking for vehicle assignment
- Pessimistic locking for the incident during dispatch
- Premium SaaS-style operations dashboard
- Incidents and fleet screens
- Leaflet live map visualization
- Local/demo operator login and logout UI
- Responsive layout and animations

## Important honesty points

The login in this version is a **frontend demo login**. It is not production authentication and does not secure the REST API.

The dashboard is action-driven REST based. It is **not WebSocket server-push real-time** yet. WebSockets can be added later as a separate learning module.

The map route network is a simplified demonstration graph. It is not a live Google/Mapbox routing service and does not use live traffic.

No AI/ML is claimed in this Java project.

## Database

Create the PostgreSQL database:

```sql
CREATE DATABASE rescueroute;
```

Default configuration:

```text
DB_URL=jdbc:postgresql://localhost:5432/rescueroute
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

If your PostgreSQL password is different, set `DB_PASSWORD` before starting Spring Boot. You can also set `DB_URL` and `DB_USERNAME`.

Hibernate uses `ddl-auto=update`, so the tables are created/updated from the JPA entities. Sample vehicles and incidents are inserted on the first empty database startup.

## Run the complete project on Windows

### 1. Backend

Open Command Prompt:

```bat
cd "C:\ShreyaResumeProjects\JAVA\RescueRoute\RescueRoutePackage\backend"
mvn clean test
mvn spring-boot:run
```

Wait for:

```text
Started RescueRouteApplication
```

The backend runs on port `8080`.

If `mvn clean test` succeeds, the JUnit tests have passed before you start the application.

### 2. Frontend

Open a second Command Prompt:

```bat
cd "C:\ShreyaResumeProjects\JAVA\RescueRoute\RescueRoutePackage\frontend"
python -m http.server 5500
```

Open:

```text
http://localhost:5500
```

Demo login:

```text
Email: operator@rescueroute.local
Password: rescueroute
```

The frontend calls the backend at `http://localhost:8080/api`. CORS is configured for ports `5500` and `127.0.0.1:5500`.

## Dispatch algorithm

```text
1. Lock the incident
2. Verify it is still PENDING
3. Find AVAILABLE vehicles
4. Remove vehicles incompatible with the emergency type
5. Select the nearest eligible vehicle
6. Use workload only as a tie-breaker
7. Lock the selected vehicle
8. Re-check vehicle availability
9. Calculate the Dijkstra route
10. Create the assignment
11. Mark incident DISPATCHED
12. Mark vehicle DISPATCHED
13. Commit the transaction
```

The locks prevent two concurrent dispatch requests from successfully assigning the same vehicle or the same pending incident.

## Vehicle lifecycle

```text
AVAILABLE → DISPATCHED → EN_ROUTE → ON_SCENE → COMPLETED → AVAILABLE
```

`OFFLINE` vehicles are not dispatchable. Invalid state transitions are rejected by the service layer.

## REST API

### Incidents
- `POST /api/incidents`
- `GET /api/incidents`
- `GET /api/incidents/{id}`
- `PUT /api/incidents/{id}/status`
- `GET /api/incidents/priority-queue`

### Vehicles
- `GET /api/vehicles`
- `GET /api/vehicles/available`
- `GET /api/vehicles/{id}`
- `PUT /api/vehicles/{id}/status`

### Dispatch
- `POST /api/dispatch`
- `GET /api/dispatches`
- `GET /api/dispatches/{id}`

### Routes
- `POST /api/routes/calculate`

### Dashboard
- `GET /api/dashboard/summary`

## Testing

The backend contains tests for the core algorithmic behavior, including Dijkstra shortest path and priority ordering. Run:

```bat
mvn clean test
```

For the concurrency behavior, test by sending two dispatch requests for the same incident/vehicle at nearly the same time and verify that only one assignment succeeds.

## Interview explanation

> RescueRoute is a Java Spring Boot emergency dispatch and route optimization system. I used a PriorityQueue to order competing emergencies by severity and creation time. For dispatching, I first filter vehicles by availability and emergency compatibility, then choose the nearest feasible vehicle with workload as a tie-breaker. I implemented Dijkstra's algorithm on a weighted graph for shortest-path calculation. The dispatch operation is transactional and uses pessimistic database locking so concurrent requests cannot assign the same vehicle or incident twice.

## Future modules

These are deliberately not fake features in the current build:

- Spring WebSocket/STOMP live push
- Production authentication and authorization
- Real GPS tracking
- Live traffic-aware routing
- Cloud deployment
- Advanced analytics
