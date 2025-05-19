package server;

import java.io.*;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Performance;
import service.PerformanceService;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final TicketServer server;
    private PrintWriter out;
    private final PerformanceService performanceService = new PerformanceService();

    public ClientHandler(Socket socket, TicketServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received command: " + inputLine);

                if (inputLine.startsWith("SELL_TICKET:")) {
                    handleTicketSale(inputLine);
                } else if (inputLine.equals("GET_PERFORMANCES")) {
                    sendPerformancesData();
                }
            }
        } catch (IOException e) {
            System.err.println("Client connection error: " + e.getMessage());
        } finally {
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private void handleTicketSale(String command) {
        try {
            String[] parts = command.split(":")[1].split(",");
            if (parts.length != 4) throw new IllegalArgumentException();

            int performanceId = Integer.parseInt(parts[0]);
            String buyerName = parts[1];
            int seats = Integer.parseInt(parts[2]);
            int employeeId = Integer.parseInt(parts[3]);

            boolean success = performanceService.sellTickets(
                    performanceId,
                    buyerName,
                    seats,
                    employeeId
            );

            if (success) {
                server.broadcastUpdate();
                out.println("SUCCESS:Tickets sold successfully");
            } else {
                out.println("ERROR:Failed to sell tickets");
            }
        } catch (Exception e) {
            out.println("ERROR:Invalid command format");
            System.err.println("Error processing ticket sale: " + e.getMessage());
        }
    }

    private void sendPerformancesData() {
        try {
            List<Performance> performances = performanceService.getAllPerformances();
            StringBuilder dataBuilder = new StringBuilder("PERFORMANCES:");

            for (Performance p : performances) {
                if (p.getArtist() == null) continue; // Handle null artist

                dataBuilder.append(String.format("%d,%d,%s,%s,%s,%d,%d;",
                        p.getPerformanceID(),
                        p.getArtist().getArtistID(),
                        sanitize(p.getArtist().getArtistName()),
                        p.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        sanitize(p.getPlace()),
                        p.getAvailableTickets(),
                        p.getSoldTickets()));
            }

            out.println(dataBuilder.toString());
        } catch (Exception e) {
            System.err.println("Error sending performances: " + e.getMessage());
            out.println("ERROR:Failed to retrieve performances");
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    private String sanitize(String input) {
        return input.replace(",", "").replace(";", "");
    }
}