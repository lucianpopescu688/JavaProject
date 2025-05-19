package service;

import model.Ticket;
import repository.ITicketRepository;
import repository.TicketRepository;
import java.time.LocalDateTime;

public class TicketService {
    private final ITicketRepository ticketRepo;

    public TicketService() {
        this.ticketRepo = new TicketRepository();
    }

    // Sell tickets for a performance
    public boolean sellTickets(int performanceId, String buyerName, int seats, int employeeId) {
        Ticket ticket = new Ticket(
                0,
                performanceId,
                buyerName,
                seats,
                LocalDateTime.now().withNano(0),
                employeeId
        );
        try {
            ticketRepo.addTicket(ticket);
            return true;
        } catch (Exception e) {
            return false; // Handle transaction failures (e.g., no seats left)
        }
    }
}