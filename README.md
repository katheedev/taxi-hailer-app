# Taxi Hailing App

## Technologies and Architecture

- **Back end**: Java 8, Spring Boot, Gradle
- **Login**: Spring Security
- **Database**: Postgres / MySQL
- **Front end**: Ant Design Pro, React
- **Inter-back-end communication**: Apache Kafka
- **Deployment**: Docker
- **Repository and Version Control**: Git, BitBucket
- **Architecture**: Micro-service architecture
## Diagrams
You can find the sequence diagram and ERD diagrams in the `diagrams` folder of this repository.

## Features

### Passenger Features

- **Login**: Passengers can log in by providing their credentials.
- **Taxi Hailing**: Passengers can hail a taxi by providing their current location and destination. A list of predefined points of the districts in Sri Lanka of latitude and longitude are used.

### Driver Features

- **Login**: Drivers can log in by providing their credentials.
- **Driving Operations**:
    - Enter Driver's Current Location: Drivers can choose their current location from predefined points of latitude and longitude.
    - Accept Trip: Drivers can accept trip requests from passengers.
    - Start Trip: Drivers can start a trip after accepting the request.
    - End Trip: Drivers can end a trip after reaching the destination.
    - Show Payment: Drivers can show the payment details, collect cash from the passenger, and update the trip status accordingly.

### Additional Features

- **Account Management**: Passengers and drivers can create/edit their accounts.
- **Availability Change**: Drivers can change their availability status, allowing them to be offline or online.
- **Location Change**: Drivers can change their location as needed.


[//]: # (## Installation and Setup)

[//]: # ()
[//]: # (### Prerequisites)

[//]: # ()
[//]: # (- Java 8 or higher)

[//]: # (- PostgreSQL)

[//]: # (- Node.js and npm)

[//]: # (- Docker)