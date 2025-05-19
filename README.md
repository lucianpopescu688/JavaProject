# Music Festival Ticket System (Java)
This project is a Java-based system for managing music festival sales. 

## Project Structure
```
JavaApp/
│
├── build.gradle
├── gradlew / gradlew.bat
├── settings.gradle
├── identifier.sqlite                    # SQLite database file
├── README.md                            # Project documentation
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/
│   │   │   │   ├── Artist.java
│   │   │   │   ├── Ticket.java
│   │   │   │   ├── OfficeWorker.java
│   │   │   │   └── Performance.java
│   │   │   ├── repository/
│   │   │   │   ├── IArtistRepository.java
│   │   │   │   ├── ArtistRepository.java
│   │   │   │   ├── ITicketRepository.java
│   │   │   │   ├── TicketRepository.java
│   │   │   │   ├── IOfficeWorkerRepository.java
│   │   │   │   ├── OfficeWorkerRepository.java
│   │   │   │   ├── IPerformanceRepository.java
│   │   │   │   └── PerformanceRepository.java
│   │   │   ├── org.example/
│   │   │       └── Main.java
│   │   └── resources/
│   │       ├── db.properties
│   │       └── log4j2.xml
│   └── test/
│       ├── java/                        
│       └── resources/                  
│
└── target/                              
    └── app.log                         
```

## Features
- User authentication (Login/Logout)
- Ticket sales tracking
- Available seat search and management
- Logging of repository actions (Log4j2)
- Database connection via configuration file (SQL)

## How to Run
1. Clone the repository.
2. Open in IntelliJ IDEA.
3. Build the project using Gradle:
   bash
   ./gradlew build
   
4. Run the application:
   bash
   ./gradlew run
   

## Homework Requirements Implemented
- Designed and implemented the *model classes*: Artist, Performance, Ticket, OfficeWorker each representing core entities of the ticket sales system with appropriate attributes, constructors and getters/setters.
- Defined *repository interfaces* for IArtistRepository, IPerformanceRepository, ITicketRepository, IOfficeWorkerRepository enabling interaction with a *relational database (SQL)* through well-structured contracts.
- Developed concrete *repository implementations*, such as ArtistRepository, PerformanceRepository, TicketRepository, OfficeWorkerRepository to execute SQL queries for retrieving performances, updating seat availability, and managing ticket sales.
- Integrated *Log4j2 logging* within repository methods to trace key actions like database reads, updates, and error handling, ensuring runtime visibility and debugging support.
- Configured *database connection management* via the DBUtils utility class, which loads connection parameters dynamically from the external db.properties file, supporting flexible and secure configuration without hardcoding values.
