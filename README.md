# RideX - Carpooling & Community Travel Application

RideX is a modern carpooling and community travel platform designed to connect drivers and co-riders for cost-effective and eco-friendly commuting. Developed using **Spring Boot** and **Thymeleaf**, RideX promotes shared rides and efficient travel, while integrating observability using **Prometheus** and **Grafana** for system monitoring.

---

## Features

- Post & discover rides
- User authentication and profiles
- Real-time map with route details (Leaflet integration)
- Coin-based reward system for ride sharing
- Ride scheduling with time filters
- Ride filters: amenities, price, time, driver rating
- System monitoring with Prometheus + Grafana

---

## Tech Stack

| Layer           | Technology                      |
|----------------|----------------------------------|
| Backend         | Spring Boot, Spring Security    |
| Frontend        | Thymeleaf, Tailwind CSS, JS     |
| Database        | MySQL                           |
| Monitoring      | Prometheus, Grafana             |
| Maps & Location | Leaflet.js, OpenStreetMap       |

---

## Screenshots

> _Coming Soon_ – Include screenshots of homepage, ride listings, and monitoring dashboard.

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/atomhudson/RideX.git
cd ridex


### 2. Configure the Database

* Create a MySQL/PostgreSQL database
* Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ridex
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build & Run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

---

## Monitoring Setup

### Prometheus + Grafana

1. **Prometheus**

   * Add Micrometer Prometheus registry dependency
   * Expose metrics at `/actuator/prometheus`

2. **Grafana**

   * Connect to Prometheus data source
   * Import dashboards for JVM, HTTP metrics, DB metrics

> Sample Dashboard: [JVM & Spring Boot Metrics](https://grafana.com/grafana/dashboards/4701)

---

## Developers

* Dhruv Saini – Backend Developer (Team Lead)
* Shikhar Rai - Frontend Developer
* Geetika Singh - Prometheus and Granfan
* Simran Upadhyay - Report, PPT and Database.


<!--

## 📜 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

```

---

Let me know if you want to split it into smaller `.md` files like:
- `monitoring.md` (for Prometheus + Grafana setup)
- `features.md`
- `contribution.md`

Or if you want to auto-generate documentation for REST APIs or DB schemas.
```
-->
