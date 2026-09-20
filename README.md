# RescueRoute

### Intelligent Emergency Dispatch and Route Optimization System

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

RescueRoute is a Java-based emergency dispatch and route optimization system designed to assist emergency operations teams in prioritizing incidents, selecting suitable response vehicles, calculating efficient routes, and managing dispatch operations through a centralized dashboard.

The system combines priority-based incident management, vehicle eligibility, shortest-path routing, vehicle state management, transactional dispatching, and PostgreSQL persistence.

---

## Overview

Emergency response operations often involve multiple incidents competing for a limited number of response vehicles. A dispatch system needs to determine which incident should be handled first, which vehicle is appropriate, and how that vehicle can reach the incident efficiently.

RescueRoute addresses these requirements through a structured dispatch workflow:

```text
Emergency
    ↓
Priority
    ↓
Vehicle Eligibility
    ↓
Vehicle Selection
    ↓
Shortest Route
    ↓
Dispatch
    ↓
Vehicle State Update
    ↓
Completion
```

---

## Features

### Emergency Incident Management

* Create and manage emergency incidents
* Classify incidents by type and severity
* Track incident status
* Prioritize competing emergencies
* Store incident location and creation time

### Vehicle Management

* Maintain emergency vehicle information
* Track vehicle availability
* Validate vehicle suitability
* Track vehicle operational states
* Maintain current vehicle assignments

### Intelligent Dispatch

* Filters unavailable vehicles
* Validates vehicle suitability for the incident
* Selects the nearest eligible vehicle
* Uses workload as a tie-breaker
* Prevents conflicting vehicle assignments through transactional processing

### Route Optimization

* Implements Dijkstra's shortest-path algorithm
* Uses a weighted graph for route calculation
* Calculates route distance
* Calculates estimated response time

### Operations Dashboard

The web interface provides:

* Incident overview
* Critical incident count
* Pending incident count
* Available vehicle count
* Incident management
* Vehicle management
* Dispatch operations
* Map-based visualization

### Map Visualization

Leaflet and OpenStreetMap are used to display:

* Emergency locations
* Vehicle locations
* Calculated routes

---

## System Architecture

```text
                    Frontend
              HTML / CSS / JavaScript
                       |
                       | REST API
                       |
                       v
                 Spring Boot
                   Backend
                       |
          +------------+------------+
          |            |            |
          v            v            v
    Controllers     Services    Algorithms
                                    |
                           +--------+--------+
                           |                 |
                           v                 v
                       Priority          Dijkstra
                       Queue             Algorithm
          |
          v
   Spring Data JPA
          |
          v
      PostgreSQL
```

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

This separation keeps API handling, business logic, persistence, and algorithmic processing independent and maintainable.

---

## Core Algorithms

### Priority Queue

Java's `PriorityQueue` is used to manage competing emergency incidents.

Incidents are ordered using:

1. Severity
2. Creation time when severity is equal

Priority order:

```text
CRITICAL
    ↓
HIGH
    ↓
MEDIUM
    ↓
LOW
```

For incidents with the same severity, the older incident is processed first.

This allows the system to retrieve the highest-priority emergency efficiently.

---

### Vehicle Selection

Vehicle selection follows a feasibility-first approach:

```text
1. Vehicle must be AVAILABLE
2. Vehicle must support the incident type
3. Select the vehicle with the shortest distance
4. Use workload as a tie-breaker
```

This prevents the system from selecting a vehicle that is closer but unsuitable for the emergency.

---

### Dijkstra's Algorithm

RescueRoute implements Dijkstra's shortest-path algorithm over a weighted graph.

The graph contains:

```text
Nodes  → Locations
Edges  → Connections
Weights → Distance
```

The algorithm calculates the shortest path from the selected vehicle to the emergency location.

The resulting distance is then used to calculate an estimated response time.

---

## Vehicle State Management

Vehicles follow a controlled lifecycle:

```text
AVAILABLE
    ↓
DISPATCHED
    ↓
EN_ROUTE
    ↓
ON_SCENE
    ↓
COMPLETED
    ↓
AVAILABLE
```

`OFFLINE` vehicles cannot be selected for dispatch.

The application prevents invalid vehicle state transitions.

---

## Dispatch Workflow

```text
Incident Created
       ↓
Incident Prioritized
       ↓
Find Eligible Vehicles
       ↓
Select Nearest Suitable Vehicle
       ↓
Re-check Vehicle Availability
       ↓
Calculate Dijkstra Route
       ↓
Calculate ETA
       ↓
Create Assignment
       ↓
Update Incident
       ↓
Update Vehicle
       ↓
Dispatch Completed
```

---

## Concurrency Handling

Vehicle assignment is a concurrency-sensitive operation.

For example, two operators may attempt to dispatch the same vehicle at nearly the same time.

RescueRoute handles this through transactional processing and database-level locking/re-validation.

The system re-checks vehicle availability before completing the assignment, reducing the risk of duplicate vehicle allocation.

---

## Database Design

### Incidents

| Field       | Description                |
| ----------- | -------------------------- |
| ID          | Unique incident identifier |
| Type        | Emergency category         |
| Severity    | Emergency priority         |
| Description | Incident details           |
| Latitude    | Incident latitude          |
| Longitude   | Incident longitude         |
| Status      | Current incident state     |
| Created At  | Incident creation time     |

### Vehicles

| Field            | Description               |
| ---------------- | ------------------------- |
| ID               | Unique vehicle identifier |
| Vehicle Number   | Fleet identifier          |
| Type             | Vehicle category          |
| Latitude         | Current latitude          |
| Longitude        | Current longitude         |
| Status           | Current vehicle state     |
| Current Incident | Active assignment         |
| Workload         | Current workload          |

### Assignments

| Field          | Description               |
| -------------- | ------------------------- |
| ID             | Assignment identifier     |
| Incident ID    | Assigned incident         |
| Vehicle ID     | Assigned vehicle          |
| Distance       | Calculated route distance |
| Estimated Time | Estimated response time   |
| Assigned At    | Assignment timestamp      |
| Completed At   | Completion timestamp      |
| Status         | Assignment state          |

---

## Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven

### Database

* PostgreSQL 18

### Frontend

* HTML5
* CSS3
* JavaScript
* Leaflet
* OpenStreetMap

### Testing

* JUnit 5
* Mockito
* Spring Boot Test

### Development

* Git
* GitHub
* IntelliJ IDEA / VS Code
* PostgreSQL

---

## Project Structure

```text
RescueRoute/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/rescueroute/
│   │   │   │   ├── algorithm/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── enums/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── styles.css
│   ├── app.js
│   └── README.txt
│
├── .gitignore
├── LICENSE
└── README.md
```

---

## REST API

### Incidents

| Method | Endpoint                        | Description               |
| ------ | ------------------------------- | ------------------------- |
| POST   | `/api/incidents`                | Create an incident        |
| GET    | `/api/incidents`                | Get all incidents         |
| GET    | `/api/incidents/{id}`           | Get an incident           |
| PUT    | `/api/incidents/{id}/status`    | Update incident status    |
| GET    | `/api/incidents/priority-queue` | Get prioritized incidents |

### Vehicles

| Method | Endpoint                    | Description            |
| ------ | --------------------------- | ---------------------- |
| GET    | `/api/vehicles`             | Get all vehicles       |
| GET    | `/api/vehicles/available`   | Get available vehicles |
| GET    | `/api/vehicles/{id}`        | Get a vehicle          |
| PUT    | `/api/vehicles/{id}/status` | Update vehicle status  |

### Dispatch

| Method | Endpoint               | Description               |
| ------ | ---------------------- | ------------------------- |
| POST   | `/api/dispatch`        | Dispatch a vehicle        |
| GET    | `/api/dispatches`      | Get dispatch assignments  |
| GET    | `/api/dispatches/{id}` | Get a dispatch assignment |

### Routes

| Method | Endpoint                | Description              |
| ------ | ----------------------- | ------------------------ |
| POST   | `/api/routes/calculate` | Calculate shortest route |

### Dashboard

| Method | Endpoint                 | Description              |
| ------ | ------------------------ | ------------------------ |
| GET    | `/api/dashboard/summary` | Get dashboard statistics |

---

## Testing

The project includes automated tests for core application logic.

Current tests cover:

* Dijkstra shortest-path calculation
* Emergency priority ordering
* Service-level decision logic

Run the tests with:

```bash
mvn clean test
```

---

## Configuration

Database credentials are externalized using environment variables.

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Production credentials should be provided through the deployment platform's environment-variable configuration rather than committed to source control.

---

## Local Setup

### Prerequisites

* Java 17 or later
* Maven
* PostgreSQL
* Git

### Database

Create a PostgreSQL database:

```sql
CREATE DATABASE rescueroute;
```

Configure the required database environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

### Run Backend

```bash
cd backend
mvn spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

### Run Frontend

```bash
cd frontend
python -m http.server 5500
```

Open:

```text
http://localhost:5500
```

---

## Limitations

The current routing system uses a controlled weighted graph rather than a production traffic-routing service.

Therefore:

* Routes are calculated from the application's configured graph.
* Travel time does not represent live traffic conditions.
* Vehicle positions are application-managed rather than GPS-tracked.
* Live server-push updates are not part of the current implementation.

These limitations define the current project scope and provide opportunities for future development.

---

## Future Enhancements

* WebSocket-based live event updates
* Role-based authentication and authorization
* Real-world road-network integration
* Live traffic-aware routing
* GPS-based vehicle tracking
* Push notifications
* Operational analytics
* Historical dispatch analysis
* Cloud deployment and monitoring

---

## Learning Outcomes

This project demonstrates practical implementation of:

* Java object-oriented programming
* Spring Boot architecture
* REST API development
* PostgreSQL database integration
* JPA/Hibernate
* Priority queues
* Graph data structures
* Dijkstra's shortest-path algorithm
* State-machine design
* Transaction management
* Concurrency handling
* Unit testing
* Frontend-backend integration

---

## Author

**Shreya S Rai**


