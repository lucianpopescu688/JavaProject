package app;


public class RestApplication {

    public static void main(String[] args) {
        // Explicitly disable JavaFX and AWT
        System.setProperty("java.awt.headless", "true");
        System.setProperty("javafx.preloader", "");
        System.setProperty("prism.order", "sw");
        System.setProperty("quantum.multithreaded", "false");

        // Disable JavaFX toolkit
        try {
            Class<?> platformClass = Class.forName("javafx.application.Platform");
            // If JavaFX is on classpath, we need to prevent it from initializing
            System.setProperty("javafx.platform", "headless");
        } catch (ClassNotFoundException e) {
            // JavaFX not on classpath, which is fine
        }

        System.out.println("Starting Music Festival Ticketing REST API Server...");
        System.out.println("Server will be available at: http://localhost:8080");
        System.out.println("Database: MusicFestivalTicketing (SQLite)");
        System.out.println("API Endpoints:");
        System.out.println("  - GET/POST http://localhost:8080/api/rest/performances");
        System.out.println("  - GET/POST http://localhost:8080/api/rest/artists");

        SpringApplication app = new SpringApplication(RestApplication.class);

        // Additional Spring Boot configurations to avoid JavaFX
        app.setHeadless(true);
        app.run(args);
    }
}