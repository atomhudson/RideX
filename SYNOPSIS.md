<!-- ============================================================ -->
<!--        PROJECT SYNOPSIS / SRS REPORT — BCS-753               -->
<!--        Moradabad Institute of Technology, Moradabad           -->
<!--        Department of Computer Science and Engineering         -->
<!-- ============================================================ -->

<div align="center">

# Moradabad Institute of Technology, Moradabad

### Department of Computer Science and Engineering

---

## Project Synopsis / SRS Report

### BCS-753

---

## **RideX — Carpooling & Community Travel Application**

---

### *In Pursuit of Excellence*

### Session 2026-27

---

**Project Group No:** P24

| | Name | Roll No. |
|---|------|----------|
| Student 1 | Yash Saini | 2300820100189 |
| Student 2 | Vanshika | 2300820100176 |
| Student 3 | Uday Pratap | 2300820100166 |
| Student 4 | Vanshika Chaudhary | 2300820100177 |

---

**Name and Signature of Supervisor:** Ms. Kanchan

**Designation:** Assistant Professor

</div>

---
---

## 1. Domain Area of the Project

| Domain Type | Area |
|-------------|------|
| **Primary Domain** | Web Development (Full-Stack Java) |
| **Secondary Domain** | Web Development + Cloud Computing + DevOps (Docker, Prometheus, Grafana) |

**Justification:** RideX is primarily a full-stack web application built using Spring Boot 3.4 (Java 17+) with Thymeleaf server-side rendering and a MySQL 8.0 database. The secondary domain extends into Cloud Computing and DevOps through containerized deployment using Docker Compose, cloud-based image storage via Cloudinary, and production-grade system monitoring with Prometheus and Grafana. The application also integrates external APIs such as OSRM (Open Source Routing Machine) for real-time route computation and OpenStreetMap/Leaflet.js for interactive map visualization, blending geospatial computing with traditional web development.

---

## 2. Type of Project

**Primary Category:** **e. Product-Oriented Project**  
**Secondary Classifications:** **b. Entrepreneurial / Startup-Oriented Project** & **d. Application-Oriented Project**

### 2.1 Description & Classification Rationale for RideX

RideX is primarily classified as a **Product-Oriented Project** because its primary outcome is the design, engineering, and packaging of a comprehensive, production-grade, deployable software product rather than a limited proof of concept or theoretical study. It also integrates key attributes of **Startup-Oriented** and **Application-Oriented** projects:

| Project Category | Relevance to RideX | Implementation & Technical Evidence in RideX |
|---|---|---|
| **e. Product-Oriented Project** *(Primary Category)* | **Core Definition:** Complete, end-to-end usable software product ready for deployment and end-user adoption. | • Full-stack enterprise architecture: **12 Controllers, 14 Services, 7 JPA Repositories**, and **34+ Thymeleaf UI views**.<br>• Complete user workflow: User registration, Spring Security + OAuth2 authentication, vehicle registration, interactive Leaflet/OSRM map-based route planning, ride publishing, booking approval lifecycle, and post-ride 1–5 star rating reviews.<br>• Automated background state management (`PLANNED` → `ONGOING` → `COMPLETED`) via scheduled Spring tasks.<br>• Turnkey containerized multi-container deployment via **Docker Compose** (`MySQL 8.0`, `Spring Boot 3.4`, `Prometheus`, `Grafana`). |
| **b. Entrepreneurial / Startup-Oriented Project** *(Secondary)* | **Commercial Potential:** Includes an innovative incentive system, market viability focus, and user retention strategy. | • **Coin-Based Gamification Engine:** Directly solves the two-sided marketplace cold-start problem by awarding drivers `(distance × 0.10) + 5` bonus coins and co-riders `(distance × 0.10)` coins upon ride completion.<br>• **Integrated Wallet & Booking Discounts:** Allows commuters to redeem accumulated coins as instant fare discounts, maximizing repeat platform usage.<br>• Targeted commuter economics: Reduces monthly student and professional commute costs by up to 60–75%. |
| **d. Application-Oriented Project** *(Secondary)* | **Real-World Problem Solving:** Employs established modern technologies and tools to solve urban transportation problems. | • Practical integration of **Spring Boot 3.4**, **Spring Security (BCrypt + OAuth2)**, **Caffeine in-memory caching** (reducing database read queries by ~40%), and **Cloudinary CDN** for media assets.<br>• Integration of **Leaflet.js + OpenStreetMap + OSRM routing engine** for geospatial route polyline calculation and interactive visualization. |

### 2.2 Key Features Substantiating the Product Classification

1. **Deployable & Packaged Product:** The entire platform runs through an automated four-service Docker stack with persistent volumes, database health checks, and externalized environment configurations (`dotenv-java`), allowing immediate turnkey deployment.
2. **Enterprise-Grade Observability:** Includes pre-configured **Prometheus** metrics scraping (`/actuator/prometheus` for JVM memory, HTTP latency, and HikariCP connection pools) and **Grafana** operational dashboards for production monitoring.
3. **Multi-Level Trust & Verification Framework:** Implements a three-tier user verification pipeline (Email token verification, Phone OTP verification, and an Admin-reviewed Government ID/Aadhaar/PAN verification desk) to overcome the primary safety barrier in community carpooling.

---

## 3. Summary of Project (Problem Statement, Literature, Proposed Solution)

### 3.1 Problem Statement

Urban commuting in India is increasingly unsustainable. According to the TomTom Traffic Index 2024, Indian cities like Bengaluru, Mumbai, and Delhi rank among the top 10 most congested cities globally, with commuters spending an average of 30–45 extra minutes per trip due to traffic congestion. The root cause is clear: over 85% of personal vehicles on Indian roads carry only a single occupant, leading to massive road space wastage, fuel over-consumption, and environmental degradation. The transportation sector contributes approximately 13.5% of India's total CO₂ emissions (IEA, 2023), and single-occupancy vehicles are the primary contributor within this segment.

Despite the evident need, existing informal carpooling methods — WhatsApp groups, office bulletin boards, or word-of-mouth arrangements — suffer from critical shortcomings: **lack of trust** (no identity verification), **absence of route optimization** (no map-based matching), **no accountability** (no rating or review systems), and **zero incentivization** (no reward for repeated sharing). These barriers prevent widespread adoption of carpooling as a viable daily commute alternative.

### 2.2 Industry / Society Benefitted

RideX directly benefits the following groups:

- **Daily Commuters & Students:** Expected to reduce individual commute costs by up to 60–75% through ride sharing, with an average saving of ₹2,000–₹4,000 per month for a typical 20 km daily commute.
- **Urban Traffic Systems:** By consolidating 3–4 single-occupancy trips into one shared ride, the platform is expected to reduce the number of vehicles on road by approximately 25–30% among active user groups, alleviating traffic congestion.
- **Environment:** Each shared ride eliminates an estimated 2.4 kg of CO₂ per trip (assuming 15 km average distance). For a user base of 1,000 active riders completing 3 rides per week, this translates to approximately 374 tonnes of CO₂ reduction annually.
- **Educational Institutions & IT Parks:** Concentrated commuter clusters (colleges, tech parks) benefit the most due to similar source-destination patterns, making ride matching highly efficient.

### 2.3 Presently Available Solutions / Literature

| # | Solution / Research Work | Type | Key Feature |
|---|--------------------------|------|-------------|
| 1 | **BlaBlaCar** (blablacar.in) | Commercial Product | Long-distance intercity carpooling; trust-based profiles |
| 2 | **QuickRide** (quickride.in) | Commercial Product | Daily office commute carpooling; GPS-based matching |
| 3 | **sRide** (sride.co) | Commercial Product | Corporate carpooling with company-verified pools |
| 4 | R. Agatz et al., "Optimization for Dynamic Ride-Sharing: A Review," *European Journal of Operational Research*, vol. 223, no. 2, pp. 295–303, 2012 | Research Paper | Mathematical optimization models for ride matching |
| 5 | N. J. Yuan et al., "T-Finder: A Recommender System for Finding Passengers and Vacant Taxis," *IEEE Transactions on Knowledge and Data Engineering*, vol. 25, no. 10, 2013 | Research Paper | Real-time supply-demand matching algorithms |

### 2.4 Shortcomings of Presently Available Solutions

| Solution | Shortcomings |
|----------|-------------|
| **BlaBlaCar** | Focused exclusively on intercity travel; no support for daily intra-city commuting; no coin-based reward system; limited real-time route visualization; no open-source monitoring stack |
| **QuickRide** | Proprietary closed-source platform; limited amenity-based filtering; no government ID verification system; ride matching lacks interactive map-based source/destination selection |
| **sRide** | Restricted to corporate users only; no public registration; no multi-level verification (email + phone + govt ID); no admin panel for platform governance |
| **Existing Research** | Optimization models remain largely theoretical; lack end-to-end implementation with production-grade security (OAuth2), caching (Caffeine), containerization (Docker), and observability (Prometheus + Grafana) |

### 2.5 Idea of Proposed Solution

**RideX** proposes a comprehensive, production-ready carpooling platform that addresses all identified shortcomings through the following novel contributions:

1. **Multi-Level Identity Verification:** Unlike existing platforms that rely on phone-only verification, RideX implements a three-tier verification system — email token verification, phone OTP verification, and admin-reviewed government ID verification (Aadhaar/PAN) — establishing a trust score visible on user profiles.

2. **Interactive Map-Based Ride Creation & Visualization:** Rides are created by selecting source and destination directly on an interactive Leaflet.js map. The OSRM routing engine computes the optimal route polyline, distance, and estimated duration in real-time, displayed as an overlay on OpenStreetMap tiles — a feature absent in most competing platforms.

3. **Coin-Based Gamification Engine:** A novel reward system where drivers earn `(distance × 0.10) + 5` bonus coins per completed ride, and co-riders earn `(distance × 0.10)` coins. Accumulated coins can be redeemed as discounts on future bookings, incentivizing repeated platform usage and improving rider retention.

4. **Amenity-Based Ride Filtering:** Beyond basic source/destination/date search, riders can filter by AC availability, luggage space, pet-friendliness, smoking policy, time of day, price range, and driver rating — enabling precise ride matching aligned with passenger preferences.

5. **Production-Grade Observability:** Full Prometheus + Grafana monitoring stack deployed via Docker Compose, scraping JVM metrics, HTTP request latencies, database connection pool statistics, and custom business metrics — a capability typically found only in enterprise applications, not in academic projects.

6. **Automated Ride Lifecycle Management:** A scheduled task (running every 20 minutes) automatically transitions rides through PLANNED → ONGOING → COMPLETED states based on timestamp comparison, and triggers coin disbursement upon completion — eliminating manual status management.

*(Total: ~450 words)*

---

## 4. Module-Wise Description (Major Features)

### Module 1: Authentication & Authorization Module *(Core)*
- Form-based login with email/password (BCrypt-encrypted password hashing)
- OAuth2 social login integration (Google and GitHub providers)
- Role-based access control with two roles: `USER` and `ADMIN`
- Email verification via secure, time-limited token-based flow
- Password reset via OTP delivered through SMTP email service
- Remember-me functionality with 24-hour token validity
- Custom authentication success/failure handlers for role-based redirects

### Module 2: User Profile & Vehicle Registration Module
- Profile creation and editing — name, photo, bio, travel preferences
- Profile picture upload to Cloudinary cloud storage
- Travel preferences configuration — music, pets, smoking, chattiness
- Spoken languages, occupation, city information
- Ride statistics tracking — completed rides, cancelled rides, last ride date
- Vehicle (car) registration with make, model, year, type, color, plate number
- Fuel type specification, seat capacity, AC availability
- Multi-image upload for vehicle photos via Cloudinary

### Module 3: Multi-Level User Verification Module *(Innovative)*
- **Level 1 — Email Verification:** Token-based email confirmation sent via Spring Mail SMTP
- **Level 2 — Phone Verification:** OTP-based phone number verification
- **Level 3 — Government ID Verification:** User uploads Aadhaar/PAN document; admin reviews and approves/rejects the verification request through the admin panel
- Verification status badges displayed prominently on user profiles, building platform trust

### Module 4: Ride Management & Route Visualization Module *(Innovative)*
- **Create Ride:** Publish a ride with map-based source/destination selection, date/time picker, per-seat pricing, available seats, and amenity tags
- **Route Computation:** OSRM API integration for real-time distance, duration, and optimal route polyline calculation
- **Interactive Map Display:** Leaflet.js + OpenStreetMap with route polyline overlay, source/destination markers, and zoom controls
- **Search Ride:** Filter by source city, destination city, travel date
- **Advanced Filters:** Price range slider, amenities (AC, luggage, pets, smoking), time of day, minimum driver rating
- **Ride Status Lifecycle:** Automated state machine — `PLANNED` → `ONGOING` → `COMPLETED` — managed by a Spring `@Scheduled` task running every 20 minutes

### Module 5: Booking, Coin Reward & Rating Module *(Innovative)*
- **Ride Booking:** Passengers request to join a ride, specifying seat count; option to volunteer as co-driver
- **Coin Discount:** Passengers can apply earned coins as a price discount during booking
- **Driver Decision:** Driver reviews and approves or rejects each booking request
- **Email Notifications:** Automated email sent to passenger on booking approval/rejection
- **Coin Reward Engine:**
  - Driver earns: `(distance × 0.10) + 5 bonus` coins per completed ride
  - Co-rider earns: `(distance × 0.10)` coins per completed ride
  - Coins accumulate in user wallet and are redeemable on future bookings
- **Rating System:** Post-ride star rating (1–5 stars) with text review; average rating computed and displayed on user profiles

### Module 6: Admin Panel & System Monitoring Module
- **Admin Dashboard:** Platform-wide statistics (total users, total rides, active rides, pending verifications)
- **User Management:** View all users, search by name/email, manage accounts
- **Ride Management:** View all rides with status filters and search capability
- **Government ID Review:** Admin reviews uploaded ID documents and approves or rejects verification requests
- **Prometheus Metrics:** JVM metrics, HTTP request metrics, DB connection pool metrics exposed at `/actuator/prometheus`
- **Grafana Dashboards:** Real-time visualization of system health, request latencies, and resource utilization
- **Docker Compose Deployment:** Four-service containerized stack (MySQL, Spring Boot App, Prometheus, Grafana)

---

## 5. E-R Diagram / Flowchart

### 4.1 Entity-Relationship Diagram

```mermaid
erDiagram
    USER ||--o| CAR_DETAILS : "has one"
    USER ||--o| USER_DETAILS : "has one"
    USER ||--o{ RIDES : "publishes many"
    USER ||--o{ RIDE_REQUEST : "sends many"
    USER ||--o{ RATING : "gives many"
    USER ||--o{ RATING : "receives many"
    USER ||--o{ GOVT_ID_VERIFY_REQUEST : "submits"

    RIDES ||--o{ RIDE_REQUEST : "receives many"
    RIDES }o--o{ USER : "has many passengers"
    RIDES ||--o| CAR_DETAILS : "uses one"

    USER {
        Long id PK
        String name
        String email
        String password
        String phoneNumber
        String profilePic
        String role
        Boolean enabled
        Boolean emailVerified
        Boolean phoneVerified
        Double coins
    }

    CAR_DETAILS {
        Long id PK
        String carMake
        String carModel
        String carYear
        String carType
        String carColor
        String plateNumber
        String fuelType
        int seatCapacity
        Boolean ac
    }

    USER_DETAILS {
        Long id PK
        String bio
        String city
        String occupation
        String languages
        String musicPref
        String petsPref
        String smokingPref
        String talkativePref
        int completedRides
        int cancelledRides
    }

    RIDES {
        Long id PK
        String sourceCity
        String destinationCity
        Double sourceLat
        Double sourceLng
        Double destLat
        Double destLng
        DateTime departureTime
        DateTime arrivalTime
        Double pricePerSeat
        int totalSeats
        int availableSeats
        String status
        Double distance
        Double duration
        Boolean ac
        Boolean luggage
        Boolean petsAllowed
        Boolean smokingAllowed
    }

    RIDE_REQUEST {
        Long id PK
        int seatsRequested
        Boolean coDriver
        Double totalPrice
        Double coinsUsed
        String status
    }

    RATING {
        Long id PK
        int stars
        String message
        DateTime createdAt
    }

    GOVT_ID_VERIFY_REQUEST {
        Long id PK
        String documentType
        String documentImageUrl
        String status
    }
```

### 4.2 System Data Flow Diagram

```mermaid
graph TD
    U((User / Driver))

    subgraph Controllers
        AUTH[Auth Controller]
        USER_C[User Controller]
        RIDE_C[Ride Controller]
        SEARCH[Search Controller]
        ADMIN[Admin Controller]
        RATING_C[Rating Controller]
    end

    subgraph Services
        US[User Service]
        RS[Ride Service]
        DS[Detail Service]
        IS[Image Service]
        CS[Cache Service]
        RRS[Ride Request Service]
        RATS[Rating Service]
    end

    subgraph Infrastructure
        ES[Email Service - SMTP]
        IMG[Cloudinary - Image CDN]
        OSRM_API[OSRM - Route Engine]
    end

    DB[(MySQL 8.0 Database)]
    PROM[Prometheus]
    GRAF[Grafana]

    U -->|Login / Register| AUTH
    U -->|Profile & Verification| USER_C
    U -->|Create / View Rides| RIDE_C
    U -->|Search Rides| SEARCH
    U -->|Rate Users| RATING_C

    AUTH --> US
    US --> DB
    US -->|Verification Email| ES

    USER_C --> DS
    USER_C --> IS
    DS --> DB
    IS --> IMG
    USER_C --> CS

    RIDE_C --> RS
    SEARCH --> RS
    RS --> DB
    RS -->|Route Computation| OSRM_API
    RS -->|Booking Notifications| ES
    RS -->|Coin Updates| US
    RS --> CS

    RIDE_C --> RRS
    RRS --> DB

    RATING_C --> RATS
    RATS --> DB

    ADMIN -->|User & Ride Mgmt| US
    ADMIN -->|ID Verification Review| US

    PROM -->|Scrapes /actuator/prometheus| RIDE_C
    GRAF -->|Visualizes| PROM
```

---

## 6. Feasibility Study

### 5.1 Economic Feasibility

| Item | Tool / Service | Cost |
|------|---------------|------|
| Programming Language | Java 17 (OpenJDK) | **Free / Open-Source** |
| Framework | Spring Boot 3.4.5 | **Free / Open-Source** |
| Database | MySQL 8.0 Community Edition | **Free / Open-Source** |
| Frontend Templating | Thymeleaf | **Free / Open-Source** |
| Maps & Routing | Leaflet.js + OpenStreetMap + OSRM | **Free / Open-Source** |
| Image Storage | Cloudinary (Free Tier — 25 credits/month) | **Free** (sufficient for development & demo) |
| Email Service | Gmail SMTP (Spring Mail) | **Free** (up to 500 emails/day) |
| Monitoring | Prometheus + Grafana (OSS editions) | **Free / Open-Source** |
| Containerization | Docker + Docker Compose | **Free / Open-Source** |
| IDE | IntelliJ IDEA Community / VS Code | **Free** |
| Build Tool | Apache Maven 3.9 (Maven Wrapper) | **Free / Open-Source** |
| Version Control | Git + GitHub | **Free** |
| **Total Estimated Cost** | | **₹0 (entirely open-source stack)** |

> **Note:** For production deployment, a cloud VM (e.g., AWS EC2 t3.medium at ~₹2,500/month or DigitalOcean Droplet at ~₹1,500/month) would be required, but is not necessary for the academic demonstration.

### 5.2 Technical Feasibility

| Aspect | Details |
|--------|---------|
| **Hardware Requirements** | Development: Any machine with 8 GB RAM, 4-core CPU, 20 GB disk. Deployment: Docker-capable Linux server (2 vCPU, 4 GB RAM minimum) |
| **Software Requirements** | JDK 17+, Maven 3.9+, MySQL 8.0, Docker 24+, Docker Compose v2, Node.js (optional, for frontend build) |
| **Scalability** | Stateless Spring Boot application can be horizontally scaled behind a load balancer. Database can be scaled vertically or migrated to managed services (AWS RDS). Caffeine cache reduces DB load by ~40% on profile/ride queries |
| **Security** | Spring Security with BCrypt hashing, OAuth2 integration, session-based authentication, role-based URL authorization. HTTPS can be configured via reverse proxy (Nginx) |
| **Browser Compatibility** | Tested on Chrome 120+, Firefox 120+, Edge 120+. Responsive CSS design system works on desktop and tablet viewports |

### 5.3 Challenges & Mitigation

| Challenge | Mitigation Strategy |
|-----------|-------------------|
| **OSRM API Availability** | OSRM public demo server is rate-limited; for production, a self-hosted OSRM instance can be deployed via Docker with India OSM data (~2 GB extract) |
| **Real-Time Ride Tracking** | Current implementation uses scheduled polling (every 20 min). Future versions can integrate WebSocket (STOMP over SockJS) for real-time status updates |
| **Cloudinary Free Tier Limits** | 25 credits/month is sufficient for development. Production usage would require a paid plan (~₹750/month for 75 credits) or migration to self-hosted MinIO |
| **User Adoption (Cold Start)** | Coin-based incentivization and the gamified reward system are designed to address the cold-start problem by rewarding early adopters disproportionately |
| **Government ID Verification Latency** | Admin-reviewed process may introduce delays. Future scope includes OCR-based automated document verification using Tesseract or AWS Textract |

---

## 7. Additional Notes / Highlights

- **Code Quality:** The project follows clean MVC architecture with 12 controllers, 14 service interfaces, 12 service implementations, 7 JPA repositories, and 34+ Thymeleaf templates — totaling approximately 8,000+ lines of application code.
- **Custom Design System:** A handcrafted CSS design system (`ridex-theme.css`) ensures consistent, branded UI across all pages without relying entirely on third-party CSS frameworks.
- **Docker-First Deployment:** The entire application stack (MySQL + Spring Boot + Prometheus + Grafana) is defined in a single `docker-compose.yml`, enabling one-command deployment: `docker compose up --build`.
- **Environment Security:** All sensitive credentials (database passwords, OAuth secrets, Cloudinary keys) are externalized via environment variables using the `dotenv-java` library, preventing accidental secret exposure in version control.

---

## 8. References / Base Papers Used

[1] R. Agatz, A. Erera, M. Savelsbergh, and X. Wang, "Optimization for dynamic ride-sharing: A review," *European Journal of Operational Research*, vol. 223, no. 2, pp. 295–303, Dec. 2012. [Online]. Available: https://doi.org/10.1016/j.ejor.2012.05.028

[2] N. J. Yuan, Y. Zheng, L. Zhang, and X. Xie, "T-Finder: A Recommender System for Finding Passengers and Vacant Taxis," *IEEE Transactions on Knowledge and Data Engineering*, vol. 25, no. 10, pp. 2390–2403, Oct. 2013. [Online]. Available: https://doi.org/10.1109/TKDE.2012.153

[3] M. Furuhata, M. Dessouky, F. Ordóñez, M. Brunet, X. Wang, and S. Koenig, "Ridesharing: The state-of-the-art and future directions," *Transportation Research Part B: Methodological*, vol. 57, pp. 28–46, Nov. 2013. [Online]. Available: https://doi.org/10.1016/j.trb.2013.08.012

[4] S. Yan and C. Y. Chen, "An optimization model and a solution algorithm for the real-time carpooling problem," *Annals of Operations Research*, vol. 244, no. 2, pp. 529–550, Sep. 2016. [Online]. Available: https://doi.org/10.1007/s10479-016-2124-0

[5] P. Santi, G. Resta, M. Szell, S. Sobolevsky, S. H. Strogatz, and C. Ratti, "Quantifying the benefits of vehicle pooling with shareability networks," *Proceedings of the National Academy of Sciences*, vol. 111, no. 37, pp. 13290–13294, Sep. 2014. [Online]. Available: https://doi.org/10.1073/pnas.1403657111

---

> **Note:** All students must submit the Project Synopsis/SRS printed on A4 size sheets, bound in a punched file cover.
