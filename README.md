# ParcelPulse - Courier Service

A modern courier service tracking system with a Spring Boot backend API and Vue 3 frontend.

## Project Overview

ParcelPulse is a comprehensive courier service platform that allows users to track parcels, schedule pickups, manage delivery preferences, and more. The application is split into two main components:

- **ParcelPulseApi**: A Spring Boot backend API that handles data storage, business logic, and API endpoints
- **ParcelPulseWeb**: A Vue 3 frontend built with Vite, Tailwind CSS, Vue Router, Axios, and Pinia for state management

## Features

- Parcel tracking with real-time status updates
- User registration and authentication
- Schedule parcel pickups and deliveries
- Shipment history and analytics
- Delivery preferences management
- Interactive tracking map
- Email/SMS notifications
- Rate calculator

## Technology Stack

### Backend (ParcelPulseApi)

- **Framework**: Spring Boot 3.1.x
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Testing**: JUnit, Mockito

### Frontend (ParcelPulseWeb)

- **Framework**: Vue 3 with Composition API
- **Build Tool**: Vite
- **CSS Framework**: Tailwind CSS
- **State Management**: Pinia
- **Routing**: Vue Router
- **HTTP Client**: Axios
- **Icons**: Font Awesome or Heroicons

## Project Structure

```
parcelpulse/
├── ParcelPulseApi/               # Spring Boot API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/parcelpulse/api/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── exception/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   └── ParcelPulseApiApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── application-dev.properties
│   │   │       └── application-prod.properties
│   │   └── test/
│   └── pom.xml
│
├── ParcelPulseWeb/               # Vue 3 frontend
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   ├── components/
│   │   │   ├── common/
│   │   │   ├── layout/
│   │   │   └── tracking/
│   │   ├── router/
│   │   ├── stores/
│   │   ├── views/
│   │   ├── services/
│   │   ├── utils/
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   └── tailwind.config.js
│
├── docker-compose.yml
├── .github/workflows/
└── README.md
```

## Database Schema

The MySQL database includes the following main entities:

1. **Users**
   - `id` (PK)
   - `email` (unique)
   - `password` (encrypted)
   - `first_name`
   - `last_name`
   - `phone_number`
   - `address`
   - `created_at`
   - `updated_at`

2. **Parcels**
   - `id` (PK)
   - `tracking_number` (unique)
   - `user_id` (FK)
   - `sender_name`
   - `sender_address`
   - `sender_postcode`
   - `recipient_name`
   - `recipient_address`
   - `recipient_postcode`
   - `weight`
   - `dimensions`
   - `status` (enum)
   - `estimated_delivery_date`
   - `created_at`
   - `updated_at`

3. **Tracking_Events**
   - `id` (PK)
   - `parcel_id` (FK)
   - `status` (enum)
   - `location`
   - `description`
   - `timestamp`

4. **Delivery_Preferences**
   - `id` (PK)
   - `user_id` (FK)
   - `safe_place_description`
   - `leave_with_neighbor`
   - `neighbor_house_number`
   - `special_instructions`
   - `signature_required`
   - `created_at`
   - `updated_at`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login (returns JWT)
- `GET /api/auth/me` - Get current user profile

### Parcels

- `POST /api/parcels` - Create a new parcel
- `GET /api/parcels` - Get all parcels (admin only)
- `GET /api/parcels/user` - Get current user's parcels
- `GET /api/parcels/{id}` - Get a specific parcel
- `PUT /api/parcels/{id}` - Update a parcel
- `DELETE /api/parcels/{id}` - Delete a parcel

### Tracking

- `GET /api/tracking/{trackingNumber}` - Get tracking information
- `POST /api/tracking/events` - Add a tracking event (admin only)
- `GET /api/tracking/events/{parcelId}` - Get all tracking events for a parcel

### Delivery Preferences

- `GET /api/preferences` - Get current user's delivery preferences
- `POST /api/preferences` - Create delivery preferences
- `PUT /api/preferences` - Update delivery preferences

## Setup and Installation

### Backend Setup

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/parcelpulse.git
   cd parcelpulse/ParcelPulseApi
   ```

2. Configure MySQL database:
   ```
   # Create database
   mysql -u root -p
   CREATE DATABASE parcelpulse;
   CREATE USER 'parcelpulse'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON parcelpulse.* TO 'parcelpulse'@'localhost';
   FLUSH PRIVILEGES;
   EXIT;
   ```

3. Configure application.properties:
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/parcelpulse
   spring.datasource.username=parcelpulse
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   
   # JWT Configuration
   app.jwt.secret=your_jwt_secret_key
   app.jwt.expiration=86400000
   
   # Server port
   server.port=8080
   ```

4. Build and run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd ../ParcelPulseWeb
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Configure API endpoint in `.env` file:
   ```
   VITE_API_URL=http://localhost:8080/api
   ```

4. Run the development server:
   ```
   npm run dev
   ```

## Deployment

### Docker Deployment

The project includes a Docker Compose configuration for easy deployment:

```bash
docker-compose up -d
```

This will start:
- MySQL database
- ParcelPulseApi Spring Boot backend
- ParcelPulseWeb Vue.js frontend
- Nginx for serving the frontend and routing API requests

### Production Deployment

For production deployment, you can:

1. Build the frontend:
   ```
   cd ParcelPulseWeb
   npm run build
   ```

2. Build the backend:
   ```
   cd ParcelPulseApi
   ./mvnw package -DskipTests
   ```

3. Deploy the JAR file to your server:
   ```
   java -jar target/parcelpulse-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## Development Workflow

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements

- Spring Boot
- Vue.js
- Tailwind CSS
- Vite
- Pinia
- Vue Router
- MySQL
- Docker